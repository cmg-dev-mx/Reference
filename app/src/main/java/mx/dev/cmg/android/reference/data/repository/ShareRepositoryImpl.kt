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
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Data Repository Implementation: Share Repository
 * 
 * Concrete implementation of ShareRepository interface.
 * Handles referral sharing functionality with unique referral links.
 * 
 * Dark Arts Implementation: Generates unique referral codes using UUID necromancy 💀⚡
 */
@Singleton
class ShareRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val shareLocalDataSource: ShareLocalDataSource,
    private val shareMapper: ShareMapper
) : ShareRepository {
    
    /**
     * Generates a unique referral link for the user
     * Uses UUID magic to ensure each invitation is traceable 🔮
     */
    private fun generateReferralLink(): String {
        val referralCode = UUID.randomUUID().toString().replace("-", "").take(8)
        return "https://app.reference.com/join?ref=$referralCode"
    }
    
    /**
     * Creates personalized referral message with unique link
     * Dark magic: Transforms generic content into compelling referral invitation ⚡
     */
    private fun createReferralMessage(originalContent: ShareContent): ShareContent {
        val referralLink = generateReferralLink()
        val personalizedMessage = """
            ¡Te invito a unirte a ${originalContent.title}! 🎉
            
            Descarga la app y obtén beneficios exclusivos usando mi código de referido:
            
            🔗 ${referralLink}
            
            ¡No te pierdas esta oportunidad increíble!
        """.trimIndent()
        
        return originalContent.copy(
            text = personalizedMessage,
            url = referralLink
        )
    }
    
    override suspend fun shareContent(content: ShareContent): Flow<ShareResult> = flow {
        try {
            if (!shareLocalDataSource.isSharingSupported()) {
                emit(ShareResult.NoAppsAvailable)
                return@flow
            }
            
            // Transform content into referral invitation using dark magic 🌟
            val referralContent = createReferralMessage(content)
            
            val chooserIntent = shareMapper.mapToChooserIntent(
                referralContent, 
                "Invitar amigos a ${content.title}"
            )
            
            // Start the chooser activity
            context.startActivity(chooserIntent)
            
            // Emit success with referral context
            emit(ShareResult.Success(
                InstalledApp(
                    packageName = "system.chooser",
                    appName = "Invitación de Referido"
                )
            ))
            
        } catch (e: Exception) {
            emit(ShareResult.Error("Error al enviar invitación: ${e.message}", e))
        }
    }
    
    override suspend fun shareToSpecificApp(
        content: ShareContent, 
        targetApp: InstalledApp
    ): Flow<ShareResult> = flow {
        try {
            // Transform content into referral invitation
            val referralContent = createReferralMessage(content)
            
            val directIntent = shareLocalDataSource.createDirectShareIntent(
                packageName = targetApp.packageName,
                title = referralContent.title,
                text = referralContent.text,
                mimeType = referralContent.mimeType
            )
            
            context.startActivity(directIntent)
            
            emit(ShareResult.Success(targetApp))
            
        } catch (e: Exception) {
            emit(ShareResult.Error(
                "Error al enviar invitación a ${targetApp.appName}: ${e.message}", 
                e
            ))
        }
    }
    
    override suspend fun isSharingAvailable(): Boolean {
        return shareLocalDataSource.isSharingSupported()
    }
    
    /**
     * Copies referral content to clipboard
     * Dark magic: Transforms content into clipboard-ready referral message 📋⚡
     */
    override suspend fun copyToClipboard(content: ShareContent): Boolean {
        // Transform content into referral invitation using dark magic 🌟
        val referralContent = createReferralMessage(content)
        
        // Create the full message to copy
        val clipboardText = """
            ${referralContent.text}
        """.trimIndent()
        
        return shareLocalDataSource.copyToClipboard(
            label = content.title,
            text = clipboardText
        )
    }
}