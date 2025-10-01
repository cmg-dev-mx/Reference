package mx.dev.cmg.android.reference.domain.usecase

import kotlinx.coroutines.flow.Flow
import mx.dev.cmg.android.reference.domain.model.ShareContent
import mx.dev.cmg.android.reference.domain.model.ShareResult
import mx.dev.cmg.android.reference.domain.repository.ShareRepository
import javax.inject.Inject

/**
 * Use Case: Share Content
 * 
 * Encapsulates the business logic for sharing content with other applications.
 * This is the main use case that handles the share functionality.
 */
class ShareContentUseCase @Inject constructor(
    private val shareRepository: ShareRepository
) {
    
    /**
     * Executes the use case to share content using system share dialog
     * @param content The content to share
     * @return Flow emitting the result of the share operation
     */
    suspend operator fun invoke(content: ShareContent): Flow<ShareResult> {
        return shareRepository.shareContent(content)
    }
}