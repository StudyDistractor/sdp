package com.github.studydistractor.sdp.user

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.firebase.ui.auth.data.model.User
import dagger.Module
import dagger.Provides
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Inject

@Module
@TestInstallIn(
    components = [ActivityComponent::class],
    replaces = [UserModule ::class]
)

object FakeCreateUserModule {

    @Provides
    fun provideFakeCreateUserModule() : UserService{
        return FakeCreateUserImplementation()
    }
}


/**
 * Fake Implementation of the CreateAccount class
 * Used for test
 */
class FakeCreateUserImplementation @Inject constructor() : UserService {


    private val users = SnapshotStateList<UserData>()
    override fun fetchUsers(): List<UserData> {
        return users
    }

    override fun postUser(user: UserData) {
        users.add(user)
    }

}