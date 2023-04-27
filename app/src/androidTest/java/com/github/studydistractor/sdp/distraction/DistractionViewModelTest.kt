package com.github.studydistractor.sdp.distraction

import com.github.studydistractor.sdp.bookmarks.FakeBookmarksService
import com.github.studydistractor.sdp.data.Distraction
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class DistractionViewModelTest {
    val name = "test"
    val description = "desc"
    val distractionId = "id123"

    @get:Rule(order = 0)
    var rule = HiltAndroidRule(this)

    @Before
    fun setup() {
        rule.inject()
    }

    @Test
    fun addDistractionThatIsNotBookmarkedWorks() {
        val distractionViewModel = DistractionViewModel(FakeBookmarksService())
        val distraction =
            Distraction(name = name, description = description, distractionId = distractionId)
        distractionViewModel.updateDistraction(distraction)
        assert(!distractionViewModel.uiState.value.isBookmarked)
    }

    @Test
    fun addDistractionToBookmarksWorks() {
        val distractionViewModel = DistractionViewModel(FakeBookmarksService())
        val distraction =
            Distraction(name = name, description = description, distractionId = distractionId)
        distractionViewModel.updateDistraction(distraction)
        distractionViewModel.handleBookmark()
        distractionViewModel.onChangedBookmark()
        assert(distractionViewModel.uiState.value.isBookmarked)
    }

    @Test
    fun removeDistractionFromBookmarksWorks() {
        val distractionViewModel = DistractionViewModel(FakeBookmarksService())
        val distraction =
            Distraction(name = name, description = description, distractionId = distractionId)
        distractionViewModel.updateDistraction(distraction)
        distractionViewModel.handleBookmark()
        distractionViewModel.onChangedBookmark()
        assert(distractionViewModel.uiState.value.isBookmarked)

        distractionViewModel.handleBookmark()
        distractionViewModel.onChangedBookmark()
        assert(!distractionViewModel.uiState.value.isBookmarked)
    }

    @Test
    fun addDistractionThatIsBookmarkedWorks() {
        val distractionViewModel = DistractionViewModel(FakeBookmarksService())
        val distraction =
            Distraction(name = name, description = description, distractionId = distractionId)
        distractionViewModel.updateDistraction(distraction)
        distractionViewModel.handleBookmark()
        distractionViewModel.updateDistraction(distraction)
        assert(distractionViewModel.uiState.value.isBookmarked)
    }
}