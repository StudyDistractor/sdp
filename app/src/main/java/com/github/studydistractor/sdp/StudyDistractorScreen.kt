package com.github.studydistractor.sdp

import android.content.Intent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.github.studydistractor.sdp.history.FirebaseHistory
import com.github.studydistractor.sdp.login.FirebaseLoginAuth
import com.github.studydistractor.sdp.maps.MapsActivity
import com.github.studydistractor.sdp.procrastinationActivity.AddProcrastinationActivityActivity
import com.github.studydistractor.sdp.register.RegisterActivity
import com.github.studydistractor.sdp.ui.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

// Inspired by https://developer.android.com/codelabs/basic-android-kotlin-compose-navigation#7

/**
 * enum values that represent the screens in the app
 */
enum class StudyDistractorScreen(@StringRes val title: Int) {
    Login(title = R.string.screen_name_login),
    Register(title = R.string.screen_name_register),
    Maps(title = R.string.screen_name_maps),
    Distraction(title = R.string.screen_name_distraction),
    CreateDistraction(title = R.string.screen_name_create_distraction),
    History(title = R.string.screen_name_history)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview("Study Distractor App")
fun StudyDistractorApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {

    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = StudyDistractorScreen.valueOf(
        backStackEntry?.destination?.route ?: StudyDistractorScreen.Login.name
    )
    val context = LocalContext.current

    Scaffold(
        topBar = { AppBarTop(
            currentScreen = currentScreen,
            canNavigateBack = navController.previousBackStackEntry != null,
            navigateUp = { navController.navigateUp() },
            goToCreateDistractionActivity = {
                context.startActivity(Intent(context, AddProcrastinationActivityActivity::class.java))
            },
            goToHistoryActivity = {
                navController.navigate(StudyDistractorScreen.History.name)
            },
            goToMapActivity = {
                context.startActivity(Intent(context, MapsActivity::class.java))
            }
        ) },
        bottomBar = { AppBarBottom(
            onHomeClick = { navController.navigate(StudyDistractorScreen.Login.name) },
            onListClick = { navController.navigate(StudyDistractorScreen.Distraction.name) },
            onMapClick = { navController.navigate(StudyDistractorScreen.Maps.name) },
            onMagicClick = { navController.navigate(StudyDistractorScreen.CreateDistraction.name) }
        ) }
    ) {
        NavHost(
            navController = navController,
            startDestination = StudyDistractorScreen.Login.name,
            modifier = modifier
                .padding(it)

        ) {
            composable(route = StudyDistractorScreen.Login.name) {
                LoginScreen(
                    onRegisterButtonClicked = {
                        context.startActivity(Intent(context, RegisterActivity::class.java))
                    },
                    onLoggedIn = {
                        navController.navigate(StudyDistractorScreen.Maps.name)
                    },
                    loginAuth = FirebaseLoginAuth(Firebase.auth) // TODO: This should be handled in the modelview
                )
            }
            composable(route = StudyDistractorScreen.Register.name) {
                RegisterScreen(
                    onRegistered = {
                        navController.navigate(StudyDistractorScreen.Maps.name)
                    }
                )
            }
            composable(route = StudyDistractorScreen.Maps.name) {
                MapsScreen(
                    /*onActivity = {
                        navController.navigate(StudyDistractorScreen.Distraction.name)
                    }*/
                )
            }
            composable(route = StudyDistractorScreen.Distraction.name) {
                DistractionScreen()
            }
            composable(route = StudyDistractorScreen.CreateDistraction.name)  {
                CreateDistractionScreen()
            }
            composable(route = StudyDistractorScreen.History.name) {
                HistoryScreen(hi = FirebaseHistory())
            }
        }
    }

    val authenticated: Boolean = true;
    val firstname: String = "Aizen"

//    if(!authenticated) {
//        return LoginScreen()
//    }


}