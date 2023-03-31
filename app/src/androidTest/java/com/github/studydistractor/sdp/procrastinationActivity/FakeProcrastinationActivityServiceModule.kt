package com.github.studydistractor.sdp.procrastinationActivity

import androidx.compose.runtime.snapshots.SnapshotStateList
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [ProcrastinationActivityServiceModule::class]
)
object FakeProcrastinationActivityServiceModule {

    @Singleton
    @Provides
    fun provideFakeService() : ProcrastinationActivityService {
        return FakeProcrastinationActivityService()
    }
}

class FakeProcrastinationActivityService : ProcrastinationActivityService{

    private val activityList = SnapshotStateList<ProcrastinationActivity>()

    override fun fetchProcrastinationActivities(callback: (List<ProcrastinationActivity>) -> Unit) {
        callback(activityList)
    }

    override fun postProcastinationActivities(activity: ProcrastinationActivity) {
        activityList.add(activity)
    }
}