package com.github.studydistractor.sdp.account

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

/**
 * This module provides the implementation of the CreateAccInterface
 * interface using Firebase authentication and Realtime Database to
 * create user accounts and store user data.
 */
@Module
@InstallIn(ActivityComponent::class)
object CreateAccountModule {

    @Provides
    fun provideCreateAccount(): CreateAccountInterface {
        return FirebaseCreateAccount()
    }

}