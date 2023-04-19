package com.github.studydistractor.sdp.history

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import dagger.Module
import dagger.Provides
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Inject

@Module
@TestInstallIn(
    components = [ActivityComponent::class],
    replaces = [HistoryModule::class]
)
class FakeHistoryModule {
    @Provides
    fun provideFakeHistoryInterface() : HistoryInterface {
        return FakeHistoryInterface()
    }
}

class FakeHistoryInterface @Inject constructor(): HistoryInterface {
    override fun getHistory(uid: String): SnapshotStateList<HistoryEntry> {
        val list = mutableStateListOf(
            HistoryEntry("test", "test", 0L),
            HistoryEntry("test", "test", 12317283L),
            HistoryEntry("test", "test", 1213212387123L),
        )
        return list
    }

    override fun getCurrentUid(): String {
        return "1234567890"
    }

    override fun addHistoryEntry(entry: HistoryEntry, uid: String): Boolean {
        return true
    }

}