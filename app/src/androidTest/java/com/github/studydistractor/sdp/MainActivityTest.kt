import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.github.studydistractor.sdp.MainActivity
import org.junit.Rule
import org.junit.Test

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MainActivityTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testNavigationButtonsAreShown() {
        setOf(
            "Home", "Map", "List of ideas", "Surprise me"
        ).forEach {
            composeTestRule.onNode(
                hasContentDescription(it)
                    .and(hasClickAction())
            ).assertIsDisplayed()
        }
    }
}