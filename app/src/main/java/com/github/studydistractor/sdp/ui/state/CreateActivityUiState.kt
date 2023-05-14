package com.github.studydistractor.sdp.ui.state

interface CreateActivityUiState {
    val name: String
    val description: String
    val supportingTextName: String
    val supportingTextDescription: String
    val latitude: String?
    val longitude: String?
}
