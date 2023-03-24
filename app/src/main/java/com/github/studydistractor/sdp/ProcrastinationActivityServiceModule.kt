package com.github.studydistractor.sdp

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object ProcrastinationActivityServiceModule {

    @Provides
    fun provideProcrastinationAcitvityService() : ProcrastinationActivityService {
        return FireBaseProcrastinationActivityService()
    }
}