package mx.dev.cmg.android.reference.domain.usecase

import kotlinx.coroutines.flow.Flow
import mx.dev.cmg.android.reference.domain.model.InstalledApp
import mx.dev.cmg.android.reference.domain.repository.AppsRepository
import javax.inject.Inject

/**
 * Use Case: Get Installed Apps
 * 
 * Encapsulates the business logic for retrieving installed applications
 * that support sharing functionality. Follows Clean Architecture principles.
 */
class GetInstalledAppsUseCase @Inject constructor(
    private val appsRepository: AppsRepository
) {
    
    /**
     * Executes the use case to get all installed apps that support sharing
     * @return Flow of list of installed apps
     */
    suspend operator fun invoke(): Flow<List<InstalledApp>> {
        return appsRepository.getInstalledApps()
    }
}