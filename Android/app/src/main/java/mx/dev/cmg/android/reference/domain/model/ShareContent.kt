package mx.dev.cmg.android.reference.domain.model

/**
 * Domain Entity: Share Content
 * 
 * Represents the content that will be shared to other applications.
 * This encapsulates the data to be shared including text, URL, or other content types.
 */
data class ShareContent(
    val title: String = "Reference App",
    val text: String = "Check out this amazing Reference App!",
    val url: String? = null,
    val mimeType: String = "text/plain",
    val extraData: Map<String, String> = emptyMap()
)

/**
 * Domain Entity: Share Result
 * 
 * Represents the result of a sharing operation.
 * Can be success, failure, or cancelled by user.
 */
sealed class ShareResult {
    data class Success(val targetApp: InstalledApp) : ShareResult()
    data class Error(val message: String, val cause: Throwable? = null) : ShareResult()
    object Cancelled : ShareResult()
    object NoAppsAvailable : ShareResult()
}