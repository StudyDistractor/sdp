package com.github.studydistractor.sdp
import android.content.Context
import android.util.Log
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegisterActivityTest {



    @get:Rule
    val composeTestRule = createAndroidComposeRule<RegisterActivity>()
    lateinit var auth : FirebaseAuth
    lateinit var db : FirebaseFirestore
    @Before
    fun before(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        FirebaseApp.initializeApp(context)
        try {
            Firebase.auth.useEmulator("10.0.2.2", 9099)
            Firebase.firestore.useEmulator("10.0.2.2", 8080)
        } catch (e: IllegalStateException) {

        }
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        auth.signOut()


    }
    @Test
    fun testRegisterButton() {

        val email = "test@gmail.com"
        val password = "123456789qwertzuiop"
        // Find the email and password text fields and enter some text
        composeTestRule.onNodeWithTag("email").performTextInput(email)
        composeTestRule.onNodeWithTag("password").performTextInput(password)
        composeTestRule.onNodeWithTag("pseudo").performTextInput("test")

        // Find the "Register" button and click it

        composeTestRule.onNodeWithTag("register").performClick()
        auth.signOut()
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            assertNotEquals(auth.currentUser, null)
            assertEquals(auth.currentUser?.email, email)
            db.collection("users").document(auth.currentUser!!.uid).get().addOnCompleteListener{ t ->
                assertEquals(t.result.get("points"), 0L)
                auth.currentUser?.delete()
            }

        }

        // Add assertions here to test the expected behavior after the button is clicked
    }
    @Test
    fun testRegisterButtonWithoutEmail() {

        // Find the email and password text fields and enter some text
        composeTestRule.onNodeWithTag("email").performTextInput("")
        composeTestRule.onNodeWithTag("password").performTextInput("123456789qwertzuiop")
        composeTestRule.onNodeWithTag("pseudo").performTextInput("test")

        // Find the "Register" button and click it
        composeTestRule.onNodeWithTag("register").performClick()
        assertEquals(auth.currentUser, null)

        // Add assertions here to test the expected behavior after the button is clicked
    }
    @Test
    fun testRegisterButtonWithoutPassword() {

        // Find the email and password text fields and enter some text
        composeTestRule.onNodeWithTag("email").performTextInput("test@example.com")
        composeTestRule.onNodeWithTag("password").performTextInput("")
        composeTestRule.onNodeWithTag("pseudo").performTextInput("test")

        // Find the "Register" button and click it
        composeTestRule.onNodeWithTag("register").performClick()
        assertEquals(auth.currentUser, null)

        // Add assertions here to test the expected behavior after the button is clicked
    }
    @Test
    fun testRegisterButtonWithoutPseudo() {

        // Find the email and password text fields and enter some text
        composeTestRule.onNodeWithTag("email").performTextInput("test@example.com")
        composeTestRule.onNodeWithTag("password").performTextInput("123456789qwertzuiop")
        composeTestRule.onNodeWithTag("pseudo").performTextInput("")

        // Find the "Register" button and click it
        composeTestRule.onNodeWithTag("register").performClick()

        // Add assertions here to test the expected behavior after the button is clicked
    }

    @Test
    fun registerFailWithoutEmail() {
        registerNewUser("","1234567890qwertzuiop","test", auth, db).addOnCompleteListener { task->
            assert(!task.isSuccessful)
        }
    }
    @Test
    fun registerFailWithoutPassword() {
        registerNewUser("test@gmail.com","","test", auth, db).addOnCompleteListener { task->
            assert(!task.isSuccessful)
        }
    }
    @Test
    fun registerFailWithoutPseudo() {
        registerNewUser("test@gmail.com","123567890qwtuiop","", auth, db).addOnCompleteListener { task->
            assert(!task.isSuccessful)
        }
    }
    @Test
    fun registerWithCorrectCredential() {
        val email = "t@gmail.com"
        val password = "asdfghjkl"
        registerNewUser(email,password,"test", auth, db).addOnCompleteListener { task->
            assertEquals(task.isSuccessful, true)
            assertNotNull(auth.currentUser)
            auth.currentUser?.delete()

        }
    }
}
