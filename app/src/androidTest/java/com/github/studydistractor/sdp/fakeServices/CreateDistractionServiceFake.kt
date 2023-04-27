package com.github.studydistractor.sdp.fakeServices

import com.github.studydistractor.sdp.createDistraction.CreateDistractionModel
import com.github.studydistractor.sdp.data.Distraction
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks

class CreateDistractionServiceFake: CreateDistractionModel {
    override fun createDistraction(distraction: Distraction): Task<Void> {
        return Tasks.whenAll(setOf(Tasks.forResult(""))) // simply succeeds immediately
    }
}