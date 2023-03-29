package com.github.studydistractor.sdp.register

import android.util.Log
import com.github.studydistractor.sdp.login.LoginAuthInterface
import com.github.studydistractor.sdp.login.LoginModule
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.testing.TestInstallIn
import java.security.InvalidParameterException
import javax.inject.Inject

@Module
@TestInstallIn(
    components = [ActivityComponent::class],
    replaces = [RegisterAuthModule::class]
)
object FakeRegisterAuthModule{

    @Provides
    fun provideFakeRegisterAuth(): RegisterAuthInterface{
        return FakeRegisterAuth()
    }
}

class FakeRegisterAuth@Inject constructor() : RegisterAuthInterface {
    var isConnected = false
    override fun createUserWithEmailAndPassword(email: String, password: String): Task<AuthResult> {
        isConnected = true
        val t = TaskCompletionSource<AuthResult>()
        if(email == null || password == null)
            t.setException(InvalidParameterException())
        t.setResult(null)
        return t.task
    }

    override fun getCurrentUser(): FirebaseUser? {
        TODO("Not yet implemented")
    }

    override fun getCurrentUserUid(): String? {
        if(!isConnected) return null
        return "123334567890"
    }
}
