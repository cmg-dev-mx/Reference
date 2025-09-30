package mx.dev.cmg.android.reference.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import mx.dev.cmg.android.reference.domain.model.AppCategory
import mx.dev.cmg.android.reference.domain.model.ShareContent
import mx.dev.cmg.android.reference.domain.model.ShareResult
import mx.dev.cmg.android.reference.domain.usecase.GetInstalledAppsUseCase
import mx.dev.cmg.android.reference.domain.usecase.ShareContentUseCase
import mx.dev.cmg.android.reference.domain.usecase.FilterAppsByCategoryUseCase
import mx.dev.cmg.android.reference.presentation.state.ShareEvent
import mx.dev.cmg.android.reference.presentation.state.ShareIntent
import mx.dev.cmg.android.reference.presentation.state.ShareState
import javax.inject.Inject

/**
 * Share Dialog ViewModel
 * 
 * Enhanced ViewModel with full business logic implementation.
 * Handles the state management for share functionality following MVI architecture.
 * Uses Clean Architecture with domain use cases.
 */
@HiltViewModel
class ShareDialogViewModel @Inject constructor(
    private val getInstalledAppsUseCase: GetInstalledAppsUseCase,
    private val shareContentUseCase: ShareContentUseCase,
    private val filterAppsByCategoryUseCase: FilterAppsByCategoryUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ShareState())
    val state: StateFlow<ShareState> = _state.asStateFlow()

    private val _events = Channel<ShareEvent>()
    val events = _events.receiveAsFlow()

    init {
        // Load installed apps when ViewModel is created
        handleIntent(ShareIntent.LoadInstalledApps)
    }

    /**
     * Handles user intents from the UI layer
     */
    fun handleIntent(intent: ShareIntent) {
        when (intent) {
            is ShareIntent.LoadInstalledApps -> loadInstalledApps()
            is ShareIntent.ShareClicked -> handleShareClicked()
            is ShareIntent.ShareToSpecificApp -> shareToSpecificApp(intent.app)
            is ShareIntent.RefreshApps -> refreshApps()
            is ShareIntent.DismissError -> dismissError()
            is ShareIntent.UpdateShareContent -> updateShareContent(intent.content)
            is ShareIntent.FilterAppsByCategory -> filterAppsByCategory(intent.category)
        }
    }

    /**
     * Loads installed apps that support sharing
     */
    private fun loadInstalledApps() {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoadingApps = true, error = null)
                
                getInstalledAppsUseCase().collect { apps ->
                    _state.value = _state.value.copy(
                        installedApps = apps,
                        isLoadingApps = false,
                        isSharingAvailable = apps.isNotEmpty()
                    )
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoadingApps = false,
                    error = "Failed to load apps: ${e.message}",
                    isSharingAvailable = false
                )
                _events.send(ShareEvent.ShowError("Failed to load installed apps"))
            }
        }
    }

    /**
     * Handles share button click - opens system share dialog
     */
    private fun handleShareClicked() {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true, error = null)
                
                val shareContent = ShareContent(
                    title = _state.value.title,
                    text = _state.value.shareContent,
                    url = _state.value.shareUrl
                )
                
                shareContentUseCase(shareContent).collect { result ->
                    _state.value = _state.value.copy(isLoading = false)
                    
                    when (result) {
                        is ShareResult.Success -> {
                            _events.send(ShareEvent.OpenSystemShareDialog)
                            _events.send(ShareEvent.ShowSuccess("Share dialog opened successfully"))
                        }
                        is ShareResult.Error -> {
                            _state.value = _state.value.copy(error = result.message)
                            _events.send(ShareEvent.ShowError(result.message))
                        }
                        is ShareResult.Cancelled -> {
                            _events.send(ShareEvent.ShareCancelled)
                        }
                        is ShareResult.NoAppsAvailable -> {
                            _state.value = _state.value.copy(error = "No apps available for sharing")
                            _events.send(ShareEvent.ShowError("No apps available for sharing"))
                        }
                    }
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Failed to share: ${e.message}"
                )
                _events.send(ShareEvent.ShowError(e.message ?: "Unknown error"))
            }
        }
    }

    /**
     * Shares content to a specific app
     */
    private fun shareToSpecificApp(app: mx.dev.cmg.android.reference.domain.model.InstalledApp) {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true, error = null)
                
                _events.send(ShareEvent.OpenSpecificApp(app))
                _events.send(ShareEvent.ShowSuccess("Sharing to ${app.appName}"))
                
                _state.value = _state.value.copy(isLoading = false)
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Failed to share to ${app.appName}: ${e.message}"
                )
                _events.send(ShareEvent.ShowError("Failed to share to ${app.appName}"))
            }
        }
    }

    /**
     * Refreshes the list of installed apps
     */
    private fun refreshApps() {
        loadInstalledApps()
    }

    /**
     * Filters apps by category
     */
    private fun filterAppsByCategory(category: AppCategory) {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoadingApps = true, error = null)
                
                filterAppsByCategoryUseCase(category).collect { filteredApps ->
                    _state.value = _state.value.copy(
                        installedApps = filteredApps,
                        isLoadingApps = false
                    )
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoadingApps = false,
                    error = "Failed to filter apps: ${e.message}"
                )
                _events.send(ShareEvent.ShowError("Failed to filter apps"))
            }
        }
    }

    /**
     * Updates the share content
     */
    private fun updateShareContent(content: String) {
        _state.value = _state.value.copy(shareContent = content)
    }

    /**
     * Dismisses the current error state
     */
    private fun dismissError() {
        _state.value = _state.value.copy(error = null)
    }
}