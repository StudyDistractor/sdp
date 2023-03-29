package com.github.studydistractor.sdp.account

import androidx.compose.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Inject

@Module
@TestInstallIn(
    components = [ActivityComponent::class],
    replaces = [CreateAccountModule ::class]
)

object FakeCreateAccModule {

    @Provides
    fun provideFakeCreateModule() : CreateAccInterface{
        return FakeCreateAccImpl()
    }
}

class FakeCreateAccImpl @Inject constructor() : CreateAccInterface {
    //fake implementation
    override fun checkNameFormat(name: String): Boolean {
        return !(name.isEmpty() || name.length >= 255)
    }

    override fun checkPhoneFormat(phoneNumber: String): Boolean {
        return !(phoneNumber.isEmpty() || phoneNumber.length != 10)
    }

    override fun postData(
        year: Int,
        month: Int,
        day: Int,
        firstname: String,
        lastname: String,
        phone: String
    ) {
        // Do Nothing
    }
}