package mx.dev.cmg.android.reference.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mx.dev.cmg.android.reference.data.repository.AppsRepositoryImpl
import mx.dev.cmg.android.reference.data.repository.ShareRepositoryImpl
import mx.dev.cmg.android.reference.domain.repository.AppsRepository
import mx.dev.cmg.android.reference.domain.repository.ShareRepository
import javax.inject.Singleton

/**
 * Data Module: Repository Bindings
 * 
 * Hilt module that provides repository implementations.
 * Binds domain repository interfaces to their data layer implementations.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    
    /**
     * Binds AppsRepository interface to its implementation
     */
    @Binds
    @Singleton
    abstract fun bindAppsRepository(
        appsRepositoryImpl: AppsRepositoryImpl
    ): AppsRepository
    
    /**
     * Binds ShareRepository interface to its implementation
     */
    @Binds
    @Singleton
    abstract fun bindShareRepository(
        shareRepositoryImpl: ShareRepositoryImpl
    ): ShareRepository
}