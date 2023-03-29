package com.github.studydistractor.sdp.register

import com.github.studydistractor.sdp.login.FirebaseLoginAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object RegisterAuthModule {
    @Provides
    fun provideRegisterAuth() : RegisterAuthInterface {
        return FirebaseRegisterAuth()
    }
//    @Provides
//    fun provideDatabase() : FirebaseDatabase{
//        return Firebase.database
//    }
}