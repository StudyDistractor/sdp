package com.github.studydistractor.sdp.procrastinationActivity

import com.github.studydistractor.sdp.procrastinationActivity.FireBaseProcrastinationActivityService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Module for Hilt injection
 */
@Module
@InstallIn(SingletonComponent::class)
object ProcrastinationActivityServiceModule {

    /**
     * Inject the implementation of the interface with Firebase realtime database
     * @return the Firebase implementation of the service
     */
    @Singleton
    @Provides
    fun provideProcrastinationAcitvityService() : ProcrastinationActivityService {
        return FireBaseProcrastinationActivityService()
    }
}