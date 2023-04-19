package com.github.studydistractor.sdp.account

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

/**
 * This module inject the implemented HistoryInterface (FirebaseHistory) for Hilt.
 */
@Module
@InstallIn(ActivityComponent::class)
object FriendsModule {
    @Provides
    fun provideUserInterface() : FriendsInterface {
        return FirebaseFriends()
    }
}