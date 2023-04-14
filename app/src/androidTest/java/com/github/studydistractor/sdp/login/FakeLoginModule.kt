package com.github.studydistractor.sdp.login

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.firebase.auth.AuthResult
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Inject

@Module
@InstallIn(ActivityComponent::class)
object FakeLoginModule {

    @Provides
    fun provideFakeLoginAuth(): LoginAuthInterface {
        return FakeLoginAuth()
    }
}

class FakeLoginAuth @Inject constructor() : LoginAuthInterface {
    override fun loginWithEmail(email: String, password: String): Task<AuthResult> {
        val t = TaskCompletionSource<AuthResult>()
        return if(email == "invalidEmail" && password == "invalidPassword"){
            t.setException(IllegalArgumentException())
            t.task
        } else if (email == "validEmail" && password == "validPassword"){
            t.setResult(null)
            t.task
        } else {
            t.setException(IllegalArgumentException())
            t.task
        }
    }
}