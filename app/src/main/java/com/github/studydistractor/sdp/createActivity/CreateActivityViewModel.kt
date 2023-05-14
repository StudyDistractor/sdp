package com.github.studydistractor.sdp.createActivity

import com.github.studydistractor.sdp.ui.state.CreateActivityUiState
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.flow.StateFlow

interface CreateActivityViewModel {

    val uiState: StateFlow<CreateActivityUiState>

    /**
     * Update the name
     *
     * @name(String): the new name
     */
    fun updateName(name: String)

    /**
     * Update the description
     *
     * @description(String): the new description
     */
    fun updateDescription(description: String)

    /**
     * Update the latitude
     * @latitude(String): the new latitude
     */
    fun updateLatitude(latitude: String)

    /**
     * Update the longitude
     * @longitude(String): the new longitude
     */
    fun updateLongitude(longitude: String)

    /**
     * Create a new activity (event or distraction) in the db
     */
    fun createActivity(): Task<Void>
}