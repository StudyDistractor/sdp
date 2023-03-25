package com.github.studydistractor.sdp

import javax.inject.Inject

interface LoginAuthInterface {
    fun login(email: String, password: String)
}