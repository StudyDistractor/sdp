package com.github.studydistractor.sdp.login

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.firebase.auth.AuthResult
import dagger.Module
import dagger.Provides
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Inject

@Module
@TestInstallIn(
    components = [ActivityComponent::class],
    replaces = [LoginModule::class]
)
object FakeLoginModule {

    @Provides
    fun provideFakeLoginAuth(): LoginAuthInterface {
        return FakeLoginAuth()
    }
}

class FakeLoginAuth @Inject constructor() : LoginAuthInterface {
    override fun loginWithEmail(email: String, password: String): Task<AuthResult> {
        var t = TaskCompletionSource<AuthResult>()
        if(email == "email" && password == "password"){
            t.setException(IllegalArgumentException())
            return t.task
        }
        t.setResult(null)
        return t.task
    }
}