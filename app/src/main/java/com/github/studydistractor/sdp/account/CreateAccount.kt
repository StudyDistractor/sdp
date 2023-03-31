package com.github.studydistractor.sdp.account

import android.util.Log
import android.widget.Toast
import androidx.compose.Context

class CreateAccount {
    object Constants {
        const val TAG = "CreateAccountActivity"
    }


    companion object {
        fun displayError(context: Context, data: String){
            Log.d(Constants.TAG, "check failed on $data")
            Toast.makeText(context, "$data is invalid", Toast.LENGTH_SHORT).show()
        }

    }
}