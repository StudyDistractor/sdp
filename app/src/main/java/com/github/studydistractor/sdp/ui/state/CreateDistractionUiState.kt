package com.github.studydistractor.sdp.ui.state

data class CreateDistractionUiState  (
    override val name: String = "",
    override val description: String = "",
    override val supportingTextName: String = "",
    override val supportingTextDescription: String = "",
    override val latitude: String? = null,
    override val longitude: String? = null,
) : CreateActivityUiState