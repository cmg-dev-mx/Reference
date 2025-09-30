package mx.dev.cmg.android.reference.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import mx.dev.cmg.android.reference.data.datasource.local.AppsLocalDataSource
import mx.dev.cmg.android.reference.data.datasource.local.ShareLocalDataSource
import mx.dev.cmg.android.reference.data.mapper.AppsMapper
import mx.dev.cmg.android.reference.data.mapper.ShareMapper
import javax.inject.Singleton

/**
 * Data Module: Data Source and Mapper Providers
 * 
 * Hilt module that provides data sources and mappers.
 * These are the building blocks for repository implementations.
 */
@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    
    /**
     * Provides AppsLocalDataSource singleton
     */
    @Provides
    @Singleton
    fun provideAppsLocalDataSource(
        @ApplicationContext context: Context
    ): AppsLocalDataSource {
        return AppsLocalDataSource(context)
    }
    
    /**
     * Provides ShareLocalDataSource singleton
     */
    @Provides
    @Singleton
    fun provideShareLocalDataSource(
        @ApplicationContext context: Context
    ): ShareLocalDataSource {
        return ShareLocalDataSource(context)
    }
    
    /**
     * Provides AppsMapper singleton
     */
    @Provides
    @Singleton
    fun provideAppsMapper(): AppsMapper {
        return AppsMapper()
    }
    
    /**
     * Provides ShareMapper singleton
     */
    @Provides
    @Singleton
    fun provideShareMapper(): ShareMapper {
        return ShareMapper()
    }
}