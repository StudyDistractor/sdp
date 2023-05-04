package com.github.studydistractor.sdp.tempActivityWrappers

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.github.studydistractor.sdp.data.Distraction
import com.github.studydistractor.sdp.distraction.DistractionServiceFirebase
import com.github.studydistractor.sdp.distraction.DistractionViewModel
import com.github.studydistractor.sdp.ui.DistractionScreen

class DistractionActivityWrapper: AppCompatActivity() {
    private val distractionViewModel = DistractionViewModel()
    private val EXTRA_ACTIVITY = "activity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //https://stackoverflow.com/questions/73019160/android-getparcelableextra-deprecated
        val procrastinationData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_ACTIVITY, Distraction::class.java)
        } else {
            intent.getParcelableExtra<Distraction>(EXTRA_ACTIVITY)
        }
        distractionViewModel.updateDistraction(procrastinationData!!)

        setContent {
            DistractionScreen(distractionViewModel = distractionViewModel)
        }
    }
}