package com.github.studydistractor.sdp.distraction

import androidx.compose.runtime.snapshots.SnapshotStateList
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DistractionServiceModule::class]
)
object FakeDistractionServiceModule {

    @Singleton
    @Provides
    fun provideFakeService() : DistractionService {
        return FakeDistractionService()
    }
}

class FakeDistractionService : DistractionService{

    private val activityList = SnapshotStateList<Distraction>()

    override fun fetchDistractions(): SnapshotStateList<Distraction> {
        return activityList
    }

    override fun postDistraction(activity: Distraction, onSuccess: () -> Unit, onFailure: () -> Unit) {
        activityList.add(activity)
        if (activityList.contains(activity)) {
            onSuccess()
        } else {
            onFailure()
        }
    }
}