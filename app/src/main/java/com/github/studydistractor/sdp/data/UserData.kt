package com.github.studydistractor.sdp.data

/**
 * Data class that represents a user
 * @param id the id of the user
 * @param firstname the firstname of the user
 * @param lastname the lastname of the user
 * @param phone the phone number of the user
 * @param birthday a timestamp of the birthday of the user
 * @param score the score of the user
 * @constructor create a user
 */
data class UserData(
    var id: String? = null,
    var firstname: String? = null,
    var lastname: String? = null,
    var phone: String? = null,
    var birthday: Int? = null,
    var score: Int?= null
)
