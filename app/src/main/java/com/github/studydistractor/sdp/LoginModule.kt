package com.github.studydistractor.sdp

import com.google.firebase.auth.FirebaseAuth
import dagger.Binds
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