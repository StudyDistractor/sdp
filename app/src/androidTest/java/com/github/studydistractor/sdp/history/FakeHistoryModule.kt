package com.github.studydistractor.sdp.history

import android.os.Parcel
import android.os.Parcelable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.firebase.database.DataSnapshot
import dagger.Module
import dagger.Provides
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.testing.TestInstallIn
import java.util.*

@Module
@TestInstallIn(
    components = [ActivityComponent::class],
    replaces = [HistoryModule::class]
)
class FakeHistoryModule {
    @Provides
    fun provideHistoryInterface() : HistoryInterface{
        return FakeHistoryInterface()
    }
}

class FakeHistoryInterface : HistoryInterface {
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