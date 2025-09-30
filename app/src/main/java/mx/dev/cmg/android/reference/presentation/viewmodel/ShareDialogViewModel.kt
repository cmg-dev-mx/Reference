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
import mx.dev.cmg.android.reference.presentation.state.ShareDialogEvent
import mx.dev.cmg.android.reference.presentation.state.ShareDialogIntent
import mx.dev.cmg.android.reference.presentation.state.ShareDialogState
import javax.inject.Inject

/**
 * Share Dialog ViewModel
 * 
 * Handles the state management for ShareDialog screen following MVI architecture.
 * Uses Hilt for dependency injection.
 * 
 * Currently implements static UI as per issue requirements (no business logic).
 * Future iterations will include actual share functionality.
 */
@HiltViewModel
class ShareDialogViewModel @Inject constructor(
    // Future: Inject use cases here for business logic
) : ViewModel() {

    private val _state = MutableStateFlow(ShareDialogState())
    val state: StateFlow<ShareDialogState> = _state.asStateFlow()

    private val _events = Channel<ShareDialogEvent>()
    val events = _events.receiveAsFlow()

    /**
     * Handles user intents from the UI layer
     */
    fun handleIntent(intent: ShareDialogIntent) {
        when (intent) {
            is ShareDialogIntent.ShareClicked -> handleShareClicked()
            is ShareDialogIntent.DismissError -> dismissError()
        }
    }

    /**
     * Handles share button click
     * Currently just sends an event, no actual sharing logic per issue requirements
     */
    private fun handleShareClicked() {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true)
                
                // TODO: Implement actual sharing logic in future iterations
                // For now, just simulate the action
                _events.send(ShareDialogEvent.OpenShareDialog)
                
                _state.value = _state.value.copy(isLoading = false)
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Failed to open share dialog: ${e.message}"
                )
                _events.send(ShareDialogEvent.ShowError(e.message ?: "Unknown error"))
            }
        }
    }

    /**
     * Dismisses the current error state
     */
    private fun dismissError() {
        _state.value = _state.value.copy(error = null)
    }
}