package mx.dev.cmg.android.reference.data.mapper

import android.content.Intent
import mx.dev.cmg.android.reference.domain.model.ShareContent
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Data Mapper: Share Mapper
 * 
 * Maps between domain entities (ShareContent) and Android system data (Intent).
 * This mapper handles the conversion of sharing content to Android Intent format.
 */
@Singleton
class ShareMapper @Inject constructor() {
    
    /**
     * Maps ShareContent domain entity to Android Intent
     * @param shareContent The domain share content
     * @return Android Intent configured for sharing
     */
    fun mapToIntent(shareContent: ShareContent): Intent {
        return Intent().apply {
            action = Intent.ACTION_SEND
            type = shareContent.mimeType
            putExtra(Intent.EXTRA_SUBJECT, shareContent.title)
            putExtra(Intent.EXTRA_TEXT, buildShareText(shareContent))
            
            // Add any extra data from the domain entity
            shareContent.extraData.forEach { (key, value) ->
                putExtra(key, value)
            }
        }
    }
    
    /**
     * Maps ShareContent to chooser Intent
     * @param shareContent The domain share content
     * @param chooserTitle The title for the chooser dialog
     * @return Android Intent configured as chooser
     */
    fun mapToChooserIntent(
        shareContent: ShareContent,
        chooserTitle: String = "Share via"
    ): Intent {
        val shareIntent = mapToIntent(shareContent)
        return Intent.createChooser(shareIntent, chooserTitle).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
    }
    
    /**
     * Builds the complete share text from ShareContent
     * @param shareContent The domain share content
     * @return Complete formatted text for sharing
     */
    private fun buildShareText(shareContent: ShareContent): String {
        return buildString {
            append(shareContent.text)
            
            // Add URL if present
            shareContent.url?.let { url ->
                if (shareContent.text.isNotEmpty()) {
                    append("\n\n")
                }
                append(url)
            }
        }
    }
}