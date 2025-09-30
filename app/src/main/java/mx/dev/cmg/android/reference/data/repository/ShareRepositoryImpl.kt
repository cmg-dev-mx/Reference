package mx.dev.cmg.android.reference.data.repository

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import mx.dev.cmg.android.reference.data.datasource.local.ShareLocalDataSource
import mx.dev.cmg.android.reference.data.mapper.ShareMapper
import mx.dev.cmg.android.reference.domain.model.InstalledApp
import mx.dev.cmg.android.reference.domain.model.ShareContent
import mx.dev.cmg.android.reference.domain.model.ShareResult
import mx.dev.cmg.android.reference.domain.repository.ShareRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Data Repository Implementation: Share Repository
 * 
 * Concrete implementation of ShareRepository interface.
 * Handles the sharing functionality using Android's Intent system.
 */
@Singleton
class ShareRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val shareLocalDataSource: ShareLocalDataSource,
    private val shareMapper: ShareMapper
) : ShareRepository {
    
    override suspend fun shareContent(content: ShareContent): Flow<ShareResult> = flow {
        try {
            if (!shareLocalDataSource.isSharingSupported()) {
                emit(ShareResult.NoAppsAvailable)
                return@flow
            }
            
            val chooserIntent = shareMapper.mapToChooserIntent(
                content, 
                "Share ${content.title}"
            )
            
            // Start the chooser activity
            context.startActivity(chooserIntent)
            
            // Since we can't directly track the result of system share chooser,
            // we emit success when the intent is successfully started
            emit(ShareResult.Success(
                InstalledApp(
                    packageName = "system.chooser",
                    appName = "System Share Chooser"
                )
            ))
            
        } catch (e: Exception) {
            emit(ShareResult.Error("Failed to share content: ${e.message}", e))
        }
    }
    
    override suspend fun shareToSpecificApp(
        content: ShareContent, 
        targetApp: InstalledApp
    ): Flow<ShareResult> = flow {
        try {
            val directIntent = shareLocalDataSource.createDirectShareIntent(
                packageName = targetApp.packageName,
                title = content.title,
                text = content.text,
                mimeType = content.mimeType
            )
            
            context.startActivity(directIntent)
            
            emit(ShareResult.Success(targetApp))
            
        } catch (e: Exception) {
            emit(ShareResult.Error(
                "Failed to share to ${targetApp.appName}: ${e.message}", 
                e
            ))
        }
    }
    
    override suspend fun isSharingAvailable(): Boolean {
        return shareLocalDataSource.isSharingSupported()
    }
}