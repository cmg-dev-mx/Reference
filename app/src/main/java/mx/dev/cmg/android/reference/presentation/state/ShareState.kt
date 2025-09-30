package mx.dev.cmg.android.reference.presentation.state

import mx.dev.cmg.android.reference.domain.model.InstalledApp

/**
 * Share State
 * 
 * Enhanced state for the share functionality with business logic.
 * Follows MVI architecture pattern with immutable data classes.
 */
data class ShareState(
    val isLoading: Boolean = false,
    val installedApps: List<InstalledApp> = emptyList(),
    val isLoadingApps: Boolean = false,
    val title: String = "Reference App",
    val description: String = "This is a demo app, to share to friends and family as a reference to the actual user account.",
    val buttonText: String = "Share with someone",
    val error: String? = null,
    val shareContent: String = "Check out this amazing Reference App!",
    val shareUrl: String? = null,
    val isSharingAvailable: Boolean = true
)

/**
 * Share Intent
 * 
 * Enhanced intents for share functionality with business logic actions.
 * Follows MVI architecture pattern with sealed interface.
 */
sealed interface ShareIntent {
    object LoadInstalledApps : ShareIntent
    object ShareClicked : ShareIntent
    data class ShareToSpecificApp(val app: InstalledApp) : ShareIntent
    object RefreshApps : ShareIntent
    object DismissError : ShareIntent
    data class UpdateShareContent(val content: String) : ShareIntent
    data class FilterAppsByCategory(val category: mx.dev.cmg.android.reference.domain.model.AppCategory) : ShareIntent
}

/**
 * Share Event
 * 
 * Enhanced events for share functionality side effects.
 * Follows MVI architecture pattern for side effects.
 */
sealed interface ShareEvent {
    object OpenSystemShareDialog : ShareEvent
    data class OpenSpecificApp(val app: InstalledApp) : ShareEvent
    data class ShowError(val message: String) : ShareEvent
    data class ShowSuccess(val message: String) : ShareEvent
    object ShareCancelled : ShareEvent
}