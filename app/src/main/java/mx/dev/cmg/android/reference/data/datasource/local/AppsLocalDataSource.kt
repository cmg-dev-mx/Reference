package mx.dev.cmg.android.reference.data.datasource.local

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Data Source: Apps Local Data Source
 * 
 * Handles access to the Android PackageManager to retrieve installed applications.
 * This is the concrete implementation that interacts with the Android system.
 */
@Singleton
class AppsLocalDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    
    private val packageManager: PackageManager = context.packageManager
    
    /**
     * Gets all installed applications that can handle ACTION_SEND intent
     * @return List of ResolveInfo representing apps that support sharing
     */
    suspend fun getShareCapableApps(): List<ResolveInfo> = withContext(Dispatchers.IO) {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
        }
        
        packageManager.queryIntentActivities(
            shareIntent,
            PackageManager.MATCH_DEFAULT_ONLY
        )
    }
    
    /**
     * Gets apps that can handle a specific MIME type
     * @param mimeType The MIME type to query for
     * @return List of ResolveInfo for compatible apps
     */
    suspend fun getAppsForMimeType(mimeType: String): List<ResolveInfo> = withContext(Dispatchers.IO) {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            type = mimeType
        }
        
        packageManager.queryIntentActivities(
            intent,
            PackageManager.MATCH_DEFAULT_ONLY
        )
    }
    
    /**
     * Gets the display name for an application
     * @param packageName The package name of the app
     * @return The display name or null if not found
     */
    suspend fun getAppDisplayName(packageName: String): String? = withContext(Dispatchers.IO) {
        try {
            val applicationInfo = packageManager.getApplicationInfo(packageName, 0)
            packageManager.getApplicationLabel(applicationInfo).toString()
        } catch (e: PackageManager.NameNotFoundException) {
            null
        }
    }
    
    /**
     * Checks if a specific package is installed
     * @param packageName The package name to check
     * @return True if installed, false otherwise
     */
    suspend fun isAppInstalled(packageName: String): Boolean = withContext(Dispatchers.IO) {
        try {
            packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }
}