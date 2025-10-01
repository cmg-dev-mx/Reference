package mx.dev.cmg.android.reference.data.mapper

import android.content.pm.ResolveInfo
import mx.dev.cmg.android.reference.domain.model.AppCategory
import mx.dev.cmg.android.reference.domain.model.InstalledApp
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Data Mapper: Apps Mapper
 * 
 * Maps between Android system data (ResolveInfo) and domain entities (InstalledApp).
 * This mapper isolates the domain layer from Android-specific data structures.
 */
@Singleton
class AppsMapper @Inject constructor() {
    
    /**
     * Maps a ResolveInfo to an InstalledApp domain entity
     * @param resolveInfo The Android system ResolveInfo
     * @param displayName The display name of the app (optional)
     * @param packageManager The PackageManager instance to load labels
     * @return InstalledApp domain entity
     */
    fun mapToInstalledApp(
        resolveInfo: ResolveInfo,
        displayName: String? = null,
        packageManager: android.content.pm.PackageManager? = null
    ): InstalledApp {
        val packageName = resolveInfo.activityInfo.packageName
        val appName = displayName ?: 
            packageManager?.let { resolveInfo.loadLabel(it).toString() } ?: 
            resolveInfo.activityInfo.name
        
        return InstalledApp(
            packageName = packageName,
            appName = appName,
            appIcon = null, // TODO: Could add icon handling here
            isShareEnabled = true,
            category = determineAppCategory(packageName)
        )
    }
    
    /**
     * Maps a list of ResolveInfo to a list of InstalledApp
     * @param resolveInfoList List of Android system ResolveInfo
     * @param displayNames Map of package names to display names
     * @param packageManager The PackageManager instance to load labels
     * @return List of InstalledApp domain entities
     */
    fun mapToInstalledAppList(
        resolveInfoList: List<ResolveInfo>,
        displayNames: Map<String, String> = emptyMap(),
        packageManager: android.content.pm.PackageManager? = null
    ): List<InstalledApp> {
        return resolveInfoList.map { resolveInfo ->
            val packageName = resolveInfo.activityInfo.packageName
            val displayName = displayNames[packageName]
            mapToInstalledApp(resolveInfo, displayName, packageManager)
        }.distinctBy { it.packageName } // Remove duplicates by package name
    }
    
    /**
     * Determines the category of an app based on its package name
     * @param packageName The package name of the app
     * @return AppCategory enum value
     */
    private fun determineAppCategory(packageName: String): AppCategory {
        return when {
            packageName.contains("whatsapp", ignoreCase = true) ||
            packageName.contains("facebook", ignoreCase = true) ||
            packageName.contains("twitter", ignoreCase = true) ||
            packageName.contains("instagram", ignoreCase = true) ||
            packageName.contains("linkedin", ignoreCase = true) ||
            packageName.contains("snapchat", ignoreCase = true) -> AppCategory.SOCIAL
            
            packageName.contains("gmail", ignoreCase = true) ||
            packageName.contains("outlook", ignoreCase = true) ||
            packageName.contains("email", ignoreCase = true) ||
            packageName.contains("mail", ignoreCase = true) -> AppCategory.EMAIL
            
            packageName.contains("telegram", ignoreCase = true) ||
            packageName.contains("signal", ignoreCase = true) ||
            packageName.contains("messages", ignoreCase = true) ||
            packageName.contains("sms", ignoreCase = true) -> AppCategory.MESSAGING
            
            packageName.contains("drive", ignoreCase = true) ||
            packageName.contains("dropbox", ignoreCase = true) ||
            packageName.contains("onedrive", ignoreCase = true) ||
            packageName.contains("cloud", ignoreCase = true) -> AppCategory.FILE_SHARING
            
            else -> AppCategory.OTHER
        }
    }
}