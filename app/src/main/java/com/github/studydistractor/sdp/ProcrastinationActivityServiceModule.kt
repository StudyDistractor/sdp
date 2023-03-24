package com.github.studydistractor.sdp

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProcrastinationActivityServiceModule {

    @Singleton
    @Provides
    fun provideProcrastinationAcitvityService() : ProcrastinationActivityService {
        return FireBaseProcrastinationActivityService()
    }
}