package com.github.studydistractor.sdp

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.github.studydistractor.sdp.procrastinationActivity.ProcrastinationActivityServiceModule
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

    override fun fetchProcrastinationActivities(): SnapshotStateList<ProcrastinationActivity> {
        return this.activityList
    }

    override fun postProcastinationActivities(activity: ProcrastinationActivity) {
        activityList.add(activity)
    }
}