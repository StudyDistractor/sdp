package com.github.studydistractor.sdp.createDistraction

import com.github.studydistractor.sdp.data.Distraction
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CreateDistractionServiceFirebase : CreateDistractionModel {
    private val pathStringProcrastinationActivity = "ProcrastinationActivities"
    private val databaseRef : DatabaseReference = FirebaseDatabase.getInstance().getReference(pathStringProcrastinationActivity)

    override fun createDistraction(distraction: Distraction): Task<Void> {
        if(distraction.name == null) {
            return Tasks.forException(Exception("Distraction has no name."))
        }
        return databaseRef.child(distraction.name).setValue(distraction)
    }
}