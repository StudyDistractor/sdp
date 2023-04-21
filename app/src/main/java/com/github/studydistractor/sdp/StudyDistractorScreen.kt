package com.github.studydistractor.sdp

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.github.studydistractor.sdp.createDistraction.CreateDistractionServiceFirebase
import com.github.studydistractor.sdp.createDistraction.CreateDistractionViewModel
import com.github.studydistractor.sdp.createUser.CreateUserServiceFirebase
import com.github.studydistractor.sdp.createUser.CreateUserViewModel
import com.github.studydistractor.sdp.distraction.DistractionServiceFirebase
import com.github.studydistractor.sdp.distraction.DistractionViewModel
import com.github.studydistractor.sdp.distractionList.DistractionListServiceFirebase
import com.github.studydistractor.sdp.distractionList.DistractionListViewModel
import com.github.studydistractor.sdp.friends.FriendsServiceFirebase
import com.github.studydistractor.sdp.friends.FriendsViewModel
import com.github.studydistractor.sdp.history.HistoryServiceFirebase
import com.github.studydistractor.sdp.history.HistoryViewModel
import com.github.studydistractor.sdp.login.LoginServiceFirebase
import com.github.studydistractor.sdp.login.LoginViewModel
import com.github.studydistractor.sdp.maps.MapsActivity
import com.github.studydistractor.sdp.register.RegisterServiceFirebase
import com.github.studydistractor.sdp.register.RegisterViewModel
import com.github.studydistractor.sdp.ui.*

// Inspired by https://developer.android.com/codelabs/basic-android-kotlin-compose-navigation#7

/**
 * enum values that represent the screens in the app
 */
enum class StudyDistractorScreen(@StringRes val title: Int) {
    Login(title = R.string.screen_name_login),
    Register(title = R.string.screen_name_register),
    Maps(title = R.string.screen_name_maps),
    DistractionList(title = R.string.screen_name_distraction_list),
    Distraction(title = R.string.screen_name_distraction),
    CreateDistraction(title = R.string.screen_name_create_distraction),
    History(title = R.string.screen_name_history),
    CreateUser(title = R.string.screen_name_create_user)
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudyDistractorApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {

    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = StudyDistractorScreen.valueOf(
        backStackEntry?.destination?.route ?: StudyDistractorScreen.Login.name
    )
    val context = LocalContext.current

    val createDistractionViewModel = CreateDistractionViewModel(CreateDistractionServiceFirebase())
    val createUserViewModel        = CreateUserViewModel(CreateUserServiceFirebase())
    val distractionListViewModel   = DistractionListViewModel(DistractionListServiceFirebase())
    val distractionViewModel       = DistractionViewModel(DistractionServiceFirebase())
    val friendsViewModel           = FriendsViewModel(FriendsServiceFirebase())
    val historyViewModel           = HistoryViewModel(HistoryServiceFirebase())
    val loginViewModel             = LoginViewModel(LoginServiceFirebase())
    val registerViewModel          = RegisterViewModel(RegisterServiceFirebase())

    Scaffold(
        topBar = { AppBarTop(
            currentScreen = currentScreen,
            canNavigateBack = navController.previousBackStackEntry != null,
            navigateUp = { navController.navigateUp() },
            goToCreateDistractionActivity = {
                navController.navigate(StudyDistractorScreen.CreateDistraction.name)
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
            onListClick = { navController.navigate(StudyDistractorScreen.DistractionList.name) },
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
                        navController.navigate(StudyDistractorScreen.Register.name)
                    },
                    onLoggedIn = {
                        navController.navigate(StudyDistractorScreen.Maps.name)
                    },
                    loginViewModel = loginViewModel
                )
            }
            composable(route = StudyDistractorScreen.Register.name) {
                RegisterScreen(
                    onSuccess = {
                        navController.navigate(StudyDistractorScreen.CreateUser.name)
                    },
                    registerViewModel = registerViewModel
                )
            }
            composable(route = StudyDistractorScreen.CreateUser.name) {
                CreateUserScreen(
                    onUserCreated = {
                        navController.navigate(StudyDistractorScreen.Maps.name)
                    },
                    createUserViewModel = createUserViewModel
                )
            }
            composable(route = StudyDistractorScreen.Maps.name) {
                MapsScreen(
                    /*onActivity = {
                        navController.navigate(StudyDistractorScreen.Distraction.name)
                    }*/
                )
            }
            composable(route = StudyDistractorScreen.DistractionList.name) {
                DistractionListScreen(
                    onDistractionClicked = { distraction ->
                        distractionViewModel.updateDistraction(distraction)
                        navController.navigate(StudyDistractorScreen.Distraction.name)
                    },
                    distractionListViewModel = distractionListViewModel
                )
            }
            composable(route = StudyDistractorScreen.Distraction.name)  {
                DistractionScreen(
                    distractionViewModel = distractionViewModel
                )
            }
            composable(route = StudyDistractorScreen.CreateDistraction.name)  {
                CreateDistractionScreen(
                    createDistractionViewModel = createDistractionViewModel,
                    onDistractionCreated = {
                        distractionListViewModel.showFilteredDistractions()
                        navController.navigate(StudyDistractorScreen.DistractionList.name)
                    }
                )
            }
            composable(route = StudyDistractorScreen.History.name) {
                HistoryScreen(
                    historyViewModel = historyViewModel
                )
            }
        }
    }
}