package com.github.studydistractor.sdp

import android.util.Log
import dagger.Binds
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
    override fun login(email: String, password: String) {
        Log.d("FakeLoginAuth","Using fake login auth !")
    }
}