package com.github.studydistractor.sdp.distraction

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

/**
 * This class help passing distraction data between views
 */
class DistractionViewModel : ViewModel(){

    var distraction by mutableStateOf<Distraction?>(null)

    /**
     * Add distraction
     *
     * @param newDistraction distraction to be added
     */
    fun addDistraction(newDistraction: Distraction) {
        distraction = newDistraction
    }
}