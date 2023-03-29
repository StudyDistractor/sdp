package com.github.studydistractor.sdp.account

import android.util.Log
import android.widget.Toast
import androidx.compose.Context
import androidx.compose.NullCompilationScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

/**
 * A class that implements the CreateAccInterface and provides methods to check if user input data
 * is valid and to create an account in Firebase database.
 */
class FirebaseCreateAccount @Inject constructor(): CreateAccInterface{

    private var auth = FirebaseAuth.getInstance()

    private var db: FirebaseDatabase = FirebaseDatabase.getInstance()

    private var PATH = "users"

    /**
     * Check if the given phone number is not empty and its length is exactly 10 characters
     * @param phoneNumber The phone number to check
     * @return true if the phone number is correctly formatted
     */
    @Inject
    override fun checkPhoneFormat(phoneNumber: String): Boolean {
        return !(phoneNumber.isEmpty() || phoneNumber.length != 10)
    }

    /**
     * Check if the given name is not empty and it does not exceed or is equal to 255 characters
     * @param name The name to check
     * @return true if the name is correctly formatted
     */
    @Inject
    override fun checkNameFormat(name: String): Boolean {
        return !(name.isEmpty() || name.length >= 255)
    }


    /**
    * Method to create a user account in Firebase database.
    * @param year the user's year of birth
    * @param month the user's month of birth
    * @param day the user's day of birth
    * @param firstname the user's first name
    * @param lastname the user's last name
    * @param phone the user's phone number
    */
    @Inject
    override fun postData(year: Int, month: Int, day: Int, firstname: String, lastname: String, phone: String){
        val userId = auth.currentUser?.uid

        if (userId != null) {
            db.getReference("users").child(userId).setValue(hashMapOf(
                "score" to 0,
                "birthday" to hashMapOf(
                    "year" to year,
                    "month" to month,
                    "day" to day
                ),
                "firstname" to firstname,
                "lastname" to lastname,
                "phone" to phone,
                "history" to hashMapOf<String, String>()
            ))
        }

    }


}