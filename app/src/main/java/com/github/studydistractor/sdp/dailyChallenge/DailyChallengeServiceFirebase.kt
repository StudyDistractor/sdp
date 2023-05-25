package com.github.studydistractor.sdp.dailyChallenge

import android.util.Log
import com.github.studydistractor.sdp.data.Distraction
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DailyChallengeServiceFirebase : DailyChallengeModel {
    object DailyChallengeConstants {
        const val TAG = "DailyChallengeService"
        const val COLLECTION_NAME = "DailyChallenges"
    }

    private val db: DatabaseReference =
        FirebaseDatabase.getInstance().getReference(DailyChallengeConstants.COLLECTION_NAME)
    private val dailyChallenges = mutableListOf<DataSnapshot>()


    init {
        db.addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (challenge in snapshot.children) {
                        dailyChallenges.add(challenge)
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    Log.w(DailyChallengeConstants.TAG, "Failed to read value.", error.toException())
                }
            }
        )
    }


    override fun fetchDailyChallenge(dateString: String): List<Distraction> {
        val challengeDistractions = mutableListOf<Distraction>()
        for (challenge in dailyChallenges) {
            if (challenge.key == dateString) {
                for (distraction in challenge.children) {
                    val distractionItem = distraction.getValue(Distraction::class.java)
                    if (distractionItem != null) {
                        challengeDistractions.add(distractionItem)
                    }
                }
            }
        }
        return challengeDistractions
    }
}