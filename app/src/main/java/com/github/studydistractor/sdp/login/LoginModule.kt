package com.github.studydistractor.sdp.login

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object LoginModule {

    @Provides
    fun provideLoginAuth() : LoginAuthInterface {
        return FirebaseLoginAuth()
    }
}