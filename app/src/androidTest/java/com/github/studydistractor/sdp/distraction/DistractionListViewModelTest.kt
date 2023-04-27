package com.github.studydistractor.sdp.distraction

import com.github.studydistractor.sdp.bookmarks.FakeBookmarksService
import com.github.studydistractor.sdp.data.Distraction
import com.github.studydistractor.sdp.distractionList.DistractionListViewModel
import com.github.studydistractor.sdp.distractionList.FakeDistractionListModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class DistractionListViewModelTest {

    private val distractionListModel = FakeDistractionListModel()
    private val bookmarksService = FakeBookmarksService()
    private val viewModel = DistractionListViewModel(bookmarksService, distractionListModel)

    @get:Rule(order = 0)
    var rule = HiltAndroidRule(this)

    @Before
    fun setup() {
        rule.inject()
    }

    @Test
    fun allDistractionsAreAddedAtInit() {
        assertEquals(distractionListModel.getAllDistractions().size, viewModel.distractions.size)
    }

    @Test
    fun filterDistractionsByLength() {
        viewModel.updateFiltersSelectedLength(Distraction.Length.SHORT)
        viewModel.filterDistractions()
        assertTrue(viewModel.distractions.all { it.length == Distraction.Length.SHORT })
    }

    @Test
    fun filterDistractionsByTags() {
        val tag = "testTag"
        viewModel.addFiltersSelectedTag(tag)
        viewModel.filterDistractions()
        assertTrue(viewModel.distractions.all { it.tags?.contains(tag) ?: false })
    }

    @Test
    fun filterDistractionsByBookmarks() {
        viewModel.updateBookmarksFilter(true)
        viewModel.filterDistractions()
        assertTrue(viewModel.distractions.all { bookmarksService.isBookmarked(it) })
    }
}