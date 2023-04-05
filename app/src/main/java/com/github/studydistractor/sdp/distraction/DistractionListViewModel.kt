package com.github.studydistractor.sdp.distraction

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel


class DistractionListViewModel(
    private val service : DistractionService
) : ViewModel(){

    var filterLength: Distraction.Length? = null
    var filterTags = setOf<String>()
    private val _distractions = mutableStateListOf<Distraction>()
    val distractions: List<Distraction> = _distractions

    init {
        _distractions.addAll(service.fetchDistractions().toList())
    }

    fun filterDistractions() {
        var filteredDistractions = service.fetchDistractions().toList()

        if(filterLength != null) {
            filteredDistractions = filteredDistractions.filter {it.length == filterLength}
        }

        if(filterTags.isNotEmpty()) {
            for(tag in filterTags) {
                filteredDistractions = filteredDistractions.filter { it.tags?.containsAll(filterTags)
                    ?: false }
            }
        }
        _distractions.clear()
        _distractions.addAll(filteredDistractions)
    }

    fun allDistractions() {
        _distractions.clear()
        _distractions.addAll(service.fetchDistractions().toList())
    }
}