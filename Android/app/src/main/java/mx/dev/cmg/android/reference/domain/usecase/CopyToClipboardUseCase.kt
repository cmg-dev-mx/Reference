package mx.dev.cmg.android.reference.domain.usecase

import mx.dev.cmg.android.reference.domain.model.ShareContent
import mx.dev.cmg.android.reference.domain.repository.ShareRepository
import javax.inject.Inject

/**
 * Use Case: Copy To Clipboard
 * 
 * Encapsulates the business logic for copying content to clipboard.
 * Handles the referral invitation message copying functionality.
 */
class CopyToClipboardUseCase @Inject constructor(
    private val shareRepository: ShareRepository
) {
    
    /**
     * Executes the use case to copy content to clipboard
     * @param content The content to copy
     * @return True if copied successfully, false otherwise
     */
    suspend operator fun invoke(content: ShareContent): Boolean {
        return shareRepository.copyToClipboard(content)
    }
}
