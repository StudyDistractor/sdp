package com.github.studydistractor.sdp.history

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object HistoryModule {
    @Provides
    fun provideRegisterAuth() : HistoryInterface{
        return FirebaseHistory()
    }
}