package com.github.studydistractor.sdp.data

/**
 * Data class that represents a user
 * @param id the id of the user
 * @param firstname the firstname of the user
 * @param lastname the lastname of the user
 * @param phone the phone number of the user
 * @param birthday a map of the birth day of the user
 * @param score the score of the user
 * @constructor create a user
 */
data class UserData(
    var id: String? = null,
    var firstname: String? = null,
    var lastname: String? = null,
    var phone: String? = null,
    var birthday: HashMap<String,Int>? = null,
    var score: Int?= null
)

