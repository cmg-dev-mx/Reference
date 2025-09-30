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
    val title: String = "Referir un Amigo",
    val description: String = "Invita a tus amigos a Boost Mobile y ambos obtendrán beneficios increíbles.",
    val buttonText: String = "Enviar Referido",
    val error: String? = null,
    val shareContent: String = "¡Te invito a unirte a Boost Mobile! Ambos obtendremos beneficios.",
    val shareUrl: String? = "https://boostmobile.com/refer?code=REF123&user=demo",
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
    object CopyToClipboardClicked : ShareIntent
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
    object CopiedToClipboard : ShareEvent
}