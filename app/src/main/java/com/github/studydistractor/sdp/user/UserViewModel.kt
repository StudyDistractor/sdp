package com.github.studydistractor.sdp.user

import android.telephony.PhoneNumberUtils
import com.github.studydistractor.sdp.data.UserData

class UserViewModel(
    userModel: UserModel
) {
    private val MAX_NAME_LENGTH = 255
    private val DATE_NUM_ELEM = 3
    private val firstname = "firstname"
    private val lastname = "lastname"

    /**
     * Check if the given phone number is a global phone number (using PhoneNumberUtils library)
     * @param phoneNumber The phone number to check
     * @throws IllegalArgumentException if the given phone number is null or invalid
     */
    private fun checkPhoneFormat(phoneNumber: String?) {
        requireNotNull(phoneNumber){"Null phone Number"}
        if (phoneNumber.isEmpty()
            || !PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)){
            throw java.lang.IllegalArgumentException("Phone number has an invalid format")
        }
    }

    /**
     * Check if the given name is not empty and it does not exceed or is equal to 255 characters
     * @param variableName the name of the variable, used in the exception message
     * @param name The name to check
     * @throws IllegalArgumentException if the name is not correctly formatted
     */
    private fun checkNameFormat(variableName: String, name: String?){
        requireNotNull(name){"$variableName is null"}
        if(name.isEmpty() || name.length >= MAX_NAME_LENGTH){
            throw java.lang.IllegalArgumentException("$variableName has an invalid format")
        }
    }

    /**
     * Check fi the given birthday date has the correct format
     * i.e it contains a key-value pair for 'year', 'month' and 'day' fields
     * @param birthday the map to check
     * @throws IllegalArgumentException if the map is not correctly formated
     */
    private fun checkBirthdayFormat(birthday: HashMap<String, Int>) {
        if (birthday.size != DATE_NUM_ELEM
            || !birthday.containsKey("day")
            || !birthday.containsKey("month")
            || !birthday.containsKey("year")
            || birthday.getValue("day") == 0
            || birthday.getValue("month") == 0
            || birthday.getValue("year") == 0){
            throw java.lang.IllegalArgumentException("Birthday has an invalid format")
        }
    }

    /**
     * Process a given User by checking if all the data is correctly initialized
     */
    fun processUser(user : UserData?, userModel: UserModel){

        requireNotNull(user){"Null User"}

        requireNotNull(user.birthday)
        checkBirthdayFormat(user.birthday!!)
        checkNameFormat(firstname, user.firstname)
        checkNameFormat(lastname, user.lastname)
        checkPhoneFormat(user.phone)

        requireNotNull(user.score){"Null Score"}
        userModel.postUser(user)
    }

}