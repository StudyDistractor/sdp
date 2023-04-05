package com.github.studydistractor.sdp.distraction

import org.junit.Assert.*
import org.junit.Test

class DistractionActivityViewModelTest {
    private val distractionViewModel = DistractionViewModel()
    @Test
    fun addDistractionThenObserveItWorks(){
        assertEquals(distractionViewModel.distraction, null)
        val name = "test"
        val description = "desc"
        val distraction = Distraction(name, description)
        this.distractionViewModel.addDistraction(distraction)
        assertEquals(this.distractionViewModel.distraction!!.name, name)
        assertEquals(this.distractionViewModel.distraction!!.description, description)
    }
}