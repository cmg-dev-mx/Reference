package mx.dev.cmg.android.reference.domain.repository

import kotlinx.coroutines.flow.Flow
import mx.dev.cmg.android.reference.domain.model.InstalledApp
import mx.dev.cmg.android.reference.domain.model.ShareContent
import mx.dev.cmg.android.reference.domain.model.ShareResult

/**
 * Domain Repository Interface: Share Repository
 * 
 * Defines the contract for sharing content with other applications.
 * This interface abstracts the sharing mechanism from the domain logic.
 * Will be implemented in the data layer using Android's Intent system.
 */
interface ShareRepository {
    
    /**
     * Shares content using the system share dialog
     * @param content The content to share
     * @return Flow emitting the result of the share operation
     */
    suspend fun shareContent(content: ShareContent): Flow<ShareResult>
    
    /**
     * Shares content directly to a specific app
     * @param content The content to share
     * @param targetApp The specific app to share to
     * @return Flow emitting the result of the share operation
     */
    suspend fun shareToSpecificApp(
        content: ShareContent, 
        targetApp: InstalledApp
    ): Flow<ShareResult>
    
    /**
     * Checks if sharing is available on the device
     * @return True if sharing is supported, false otherwise
     */
    suspend fun isSharingAvailable(): Boolean
    
    /**
     * Copies content to clipboard
     * @param content The content to copy
     * @return True if copied successfully, false otherwise
     */
    suspend fun copyToClipboard(content: ShareContent): Boolean
}