package mx.dev.cmg.android.reference.domain.usecase

import kotlinx.coroutines.flow.Flow
import mx.dev.cmg.android.reference.domain.model.AppCategory
import mx.dev.cmg.android.reference.domain.model.InstalledApp
import mx.dev.cmg.android.reference.domain.repository.AppsRepository
import javax.inject.Inject

/**
 * Use Case: Filter Apps by Category
 * 
 * Encapsulates the business logic for filtering installed applications
 * by their category (social, email, messaging, etc.).
 */
class FilterAppsByCategoryUseCase @Inject constructor(
    private val appsRepository: AppsRepository
) {
    
    /**
     * Executes the use case to filter apps by category
     * @param category The category to filter by
     * @return Flow of filtered apps
     */
    suspend operator fun invoke(category: AppCategory): Flow<List<InstalledApp>> {
        return appsRepository.getAppsByCategory(category)
    }
}