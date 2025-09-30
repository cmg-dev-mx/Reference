package mx.dev.cmg.android.reference.domain.usecase

import kotlinx.coroutines.flow.Flow
import mx.dev.cmg.android.reference.domain.model.InstalledApp
import mx.dev.cmg.android.reference.domain.repository.AppsRepository
import javax.inject.Inject

/**
 * Use Case: Get Compatible Apps for Content
 * 
 * Encapsulates the business logic for finding apps that are compatible
 * with a specific content type (MIME type).
 */
class GetCompatibleAppsUseCase @Inject constructor(
    private val appsRepository: AppsRepository
) {
    
    /**
     * Executes the use case to get apps compatible with a MIME type
     * @param mimeType The MIME type to check compatibility for
     * @return Flow of compatible apps
     */
    suspend operator fun invoke(mimeType: String): Flow<List<InstalledApp>> {
        return appsRepository.getAppsForMimeType(mimeType)
    }
}