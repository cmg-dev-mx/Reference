package mx.dev.cmg.android.reference.domain.repository

import kotlinx.coroutines.flow.Flow
import mx.dev.cmg.android.reference.domain.model.InstalledApp
import mx.dev.cmg.android.reference.domain.model.ShareContent
import mx.dev.cmg.android.reference.domain.model.ShareResult

/**
 * Domain Repository Interface: Apps Repository
 * 
 * Defines the contract for accessing installed applications.
 * This interface lives in the domain layer and will be implemented in the data layer.
 * Follows Clean Architecture principles with no Android dependencies.
 */
interface AppsRepository {
    
    /**
     * Gets all installed applications that support sharing
     * @return Flow of list of installed apps that can receive share intents
     */
    suspend fun getInstalledApps(): Flow<List<InstalledApp>>
    
    /**
     * Filters apps by category
     * @param category The category to filter by
     * @return Flow of filtered apps
     */
    suspend fun getAppsByCategory(category: mx.dev.cmg.android.reference.domain.model.AppCategory): Flow<List<InstalledApp>>
    
    /**
     * Gets apps that support a specific MIME type
     * @param mimeType The MIME type to check compatibility for
     * @return Flow of compatible apps
     */
    suspend fun getAppsForMimeType(mimeType: String): Flow<List<InstalledApp>>
}