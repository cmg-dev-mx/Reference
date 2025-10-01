package mx.dev.cmg.android.reference.data.datasource.local

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Data Source: Share Local Data Source
 * 
 * Handles the sharing functionality using Android's Intent system.
 * This class manages the actual sharing operations with other applications.
 */
@Singleton
class ShareLocalDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    
    /**
     * Creates a share intent for the given content
     * @param title The title/subject of the content
     * @param text The text content to share
     * @param mimeType The MIME type of the content
     * @return Intent configured for sharing
     */
    suspend fun createShareIntent(
        title: String,
        text: String,
        mimeType: String = "text/plain"
    ): Intent = withContext(Dispatchers.Main) {
        Intent().apply {
            action = Intent.ACTION_SEND
            type = mimeType
            putExtra(Intent.EXTRA_SUBJECT, title)
            putExtra(Intent.EXTRA_TEXT, text)
        }
    }
    
    /**
     * Creates a chooser intent for sharing
     * @param shareIntent The base share intent
     * @param title The title for the chooser dialog
     * @return Intent configured as a chooser
     */
    suspend fun createChooserIntent(
        shareIntent: Intent,
        title: String = "Share via"
    ): Intent = withContext(Dispatchers.Main) {
        Intent.createChooser(shareIntent, title).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
    }
    
    /**
     * Creates an intent to share directly to a specific app
     * @param packageName The target app's package name
     * @param className The target activity class name (optional)
     * @param title The title/subject of the content
     * @param text The text content to share
     * @param mimeType The MIME type of the content
     * @return Intent configured for the specific app
     */
    suspend fun createDirectShareIntent(
        packageName: String,
        className: String? = null,
        title: String,
        text: String,
        mimeType: String = "text/plain"
    ): Intent = withContext(Dispatchers.Main) {
        Intent().apply {
            action = Intent.ACTION_SEND
            type = mimeType
            setPackage(packageName)
            className?.let { setClassName(packageName, it) }
            putExtra(Intent.EXTRA_SUBJECT, title)
            putExtra(Intent.EXTRA_TEXT, text)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
    }
    
    /**
     * Checks if the device supports sharing
     * @return True if sharing is supported
     */
    suspend fun isSharingSupported(): Boolean = withContext(Dispatchers.IO) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
        }
        
        val packageManager = context.packageManager
        shareIntent.resolveActivity(packageManager) != null
    }
    
    /**
     * Copies text to clipboard
     * @param label User-visible label for the clip data
     * @param text The text to copy to clipboard
     * @return True if the text was successfully copied
     */
    suspend fun copyToClipboard(
        label: String,
        text: String
    ): Boolean = withContext(Dispatchers.Main) {
        try {
            val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
            if (clipboardManager != null) {
                val clip = ClipData.newPlainText(label, text)
                clipboardManager.setPrimaryClip(clip)
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }
}