package mx.dev.cmg.android.reference.data.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import mx.dev.cmg.android.reference.data.datasource.local.AppsLocalDataSource
import mx.dev.cmg.android.reference.data.mapper.AppsMapper
import mx.dev.cmg.android.reference.domain.model.AppCategory
import mx.dev.cmg.android.reference.domain.model.InstalledApp
import mx.dev.cmg.android.reference.domain.repository.AppsRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Data Repository Implementation: Apps Repository
 * 
 * Concrete implementation of AppsRepository interface.
 * Coordinates between data sources and mappers to provide domain entities.
 */
@Singleton
class AppsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val appsLocalDataSource: AppsLocalDataSource,
    private val appsMapper: AppsMapper
) : AppsRepository {
    
    override suspend fun getInstalledApps(): Flow<List<InstalledApp>> = flow {
        try {
            val resolveInfoList = appsLocalDataSource.getShareCapableApps()
            
            // Get display names for all apps
            val displayNames = mutableMapOf<String, String>()
            resolveInfoList.forEach { resolveInfo ->
                val packageName = resolveInfo.activityInfo.packageName
                val displayName = appsLocalDataSource.getAppDisplayName(packageName)
                displayName?.let { displayNames[packageName] = it }
            }
            
            val installedApps = appsMapper.mapToInstalledAppList(
                resolveInfoList, 
                displayNames, 
                context.packageManager
            )
            emit(installedApps)
        } catch (e: Exception) {
            emit(emptyList()) // Emit empty list in case of error
        }
    }
    
    override suspend fun getAppsByCategory(category: AppCategory): Flow<List<InstalledApp>> = flow {
        try {
            val allApps = mutableListOf<InstalledApp>()
            getInstalledApps().collect { apps ->
                allApps.addAll(apps.filter { it.category == category })
            }
            emit(allApps)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }
    
    override suspend fun getAppsForMimeType(mimeType: String): Flow<List<InstalledApp>> = flow {
        try {
            val resolveInfoList = appsLocalDataSource.getAppsForMimeType(mimeType)
            
            // Get display names for all apps
            val displayNames = mutableMapOf<String, String>()
            resolveInfoList.forEach { resolveInfo ->
                val packageName = resolveInfo.activityInfo.packageName
                val displayName = appsLocalDataSource.getAppDisplayName(packageName)
                displayName?.let { displayNames[packageName] = it }
            }
            
            val installedApps = appsMapper.mapToInstalledAppList(
                resolveInfoList, 
                displayNames, 
                context.packageManager
            )
            emit(installedApps)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }
}