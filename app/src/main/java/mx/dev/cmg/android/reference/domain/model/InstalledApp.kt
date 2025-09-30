package mx.dev.cmg.android.reference.domain.model

/**
 * Domain Entity: Installed App
 * 
 * Represents an installed application that supports sharing.
 * This is a pure domain entity with no Android dependencies.
 * Follows Clean Architecture principles.
 */
data class InstalledApp(
    val packageName: String,
    val appName: String,
    val appIcon: String? = null, // Base64 encoded icon or resource identifier
    val isShareEnabled: Boolean = true,
    val category: AppCategory = AppCategory.OTHER
)

/**
 * Categories of applications for sharing
 */
enum class AppCategory {
    SOCIAL,      // WhatsApp, Facebook, Twitter, etc.
    EMAIL,       // Gmail, Outlook, etc.
    MESSAGING,   // Telegram, Signal, etc.
    FILE_SHARING, // Google Drive, Dropbox, etc.
    OTHER        // Any other app that supports sharing
}