package com.github.studydistractor.sdp.distraction

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class DistractionListViewModelTest {
    private val distractions = listOf(
        Distraction("test", "test"),
        Distraction("short", "test", length = Distraction.Length.SHORT),
        Distraction("medium", "test", length = Distraction.Length.MEDIUM),
        Distraction("tags", "test", tags = listOf("testTag")),
        Distraction("mix", "test", length = Distraction.Length.MEDIUM, tags = listOf("testTag"))
    )

    @get:Rule(order = 0)
    var rule = HiltAndroidRule(this)

    @Inject
    lateinit var fakeService: DistractionService

    @Before
    fun setup() {
        rule.inject()
        for (distraction in distractions) {
            fakeService.postDistraction(distraction, {}, {})
        }
    }

    @Test
    fun allDistractionsAreAddedAtInit() {
        val viewModel = DistractionListViewModel(fakeService)
        assertEquals(distractions.size, viewModel.distractions.size)
        assert(viewModel.distractions.containsAll(distractions))
    }

    @Test
    fun filterWithTagsReturnEmptyIfNoMatching() {
        val viewModel = DistractionListViewModel(fakeService)
        viewModel.filterTags = setOf("nosuchtag")
        viewModel.filterDistractions()
        assertEquals(0, viewModel.distractions.size)
    }

    @Test
    fun filterWithTagsReturnFilteredDistraction() {
        val viewModel = DistractionListViewModel(fakeService)
        viewModel.filterTags = setOf("testTag")
        viewModel.filterDistractions()
        assertEquals(2, viewModel.distractions.size)
        assertTrue(viewModel.distractions.contains(distractions[3]))
    }

    @Test
    fun filterWithLengthWorks() {
        val viewModel = DistractionListViewModel(fakeService)
        viewModel.filterLength = Distraction.Length.SHORT
        viewModel.filterDistractions()
        assertEquals(1, viewModel.distractions.size)
        assertTrue(viewModel.distractions.contains(distractions[1]))
    }

    @Test
    fun nothingIsFilteredIfNullLengthAndEmptyTags() {
        val viewModel = DistractionListViewModel(fakeService)
        viewModel.filterDistractions()
        assertEquals(distractions.size, viewModel.distractions.size)
        assertTrue(viewModel.distractions.containsAll(distractions))
    }

    @Test
    fun filterAllThenAllDistractionsCallWorks() {
        val viewModel = DistractionListViewModel(fakeService)
        viewModel.filterTags = setOf("nosuchtag")
        viewModel.filterDistractions()
        assertEquals(0, viewModel.distractions.size)
        viewModel.allDistractions()
        assertEquals(distractions.size, viewModel.distractions.size)
        assertTrue(viewModel.distractions.containsAll(distractions))
    }

    @Test
    fun checkingDefaultValue() {
        val viewModel = DistractionListViewModel(fakeService)
        assertEquals(null, viewModel.filterLength)
        assertEquals(0, viewModel.filterTags.size)
    }
}