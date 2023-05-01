package com.github.studydistractor.sdp.createDistraction

import com.github.studydistractor.sdp.distraction.Distraction
import com.google.android.gms.tasks.Task

interface CreateDistractionModel {
    fun createDistraction(distraction: Distraction): Task<Void>
}