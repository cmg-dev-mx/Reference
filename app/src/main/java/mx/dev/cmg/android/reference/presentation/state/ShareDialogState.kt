package mx.dev.cmg.android.reference.presentation.state

/**
 * Share Dialog State
 * 
 * Represents the UI state for the ShareDialog screen.
 * Follows MVI architecture pattern with immutable data classes.
 */
data class ShareDialogState(
    val isLoading: Boolean = false,
    val title: String = "Reference App",
    val description: String = "This is a demo app, to share to friends and family as a reference to the actual user account.",
    val buttonText: String = "Share with someone",
    val error: String? = null
)

/**
 * Share Dialog Intent
 * 
 * Represents user intents/actions for the ShareDialog screen.
 * Follows MVI architecture pattern with sealed interface.
 */
sealed interface ShareDialogIntent {
    object ShareClicked : ShareDialogIntent
    object DismissError : ShareDialogIntent
}

/**
 * Share Dialog Event
 * 
 * Represents one-time events that should be handled by the UI.
 * Follows MVI architecture pattern for side effects.
 */
sealed interface ShareDialogEvent {
    object OpenShareDialog : ShareDialogEvent
    data class ShowError(val message: String) : ShareDialogEvent
}