package com.github.studydistractor.sdp.account

/**
 * This interface defines the contract for creating a user account on Firebase.
 */
interface CreateAccountInterface {

    /**
     * Check if the given name has the good format
     * @param name The name to check
     * @return true if the name is correctly formatted
     */
    fun checkNameFormat(name: String): Boolean

    /**
     * Check if the given phone number has the good format
     * @param phoneNumber The phone number to check
     * @return true if the phone number is correctly formatted
     */
    fun checkPhoneFormat(phoneNumber: String): Boolean

    /**
    * Posts the given user data to Firebase database.
    * @param year The birth year of the user.
    * @param month The birth month of the user.
    * @param day The birth day of the user.
    * @param firstname The first name of the user.
    * @param lastname The last name of the user.
    * @param phone The phone number of the user.
    */
    fun postData(year: Int, month: Int, day: Int, firstname: String, lastname: String, phone: String)
}
