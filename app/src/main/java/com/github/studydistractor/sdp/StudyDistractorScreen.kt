package com.github.studydistractor.sdp

import android.content.Intent
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.github.studydistractor.sdp.account.FriendsServiceFirebase
import com.github.studydistractor.sdp.createDistraction.CreateDistractionServiceFirebase
import com.github.studydistractor.sdp.createDistraction.CreateDistractionViewModel
import com.github.studydistractor.sdp.createEvent.CreateEventServiceFirebase
import com.github.studydistractor.sdp.createEvent.CreateEventViewModel
import com.github.studydistractor.sdp.createUser.CreateUserServiceFirebase
import com.github.studydistractor.sdp.createUser.CreateUserViewModel
import com.github.studydistractor.sdp.dailyChallenge.DailyChallengeServiceFirebase
import com.github.studydistractor.sdp.dailyChallenge.DailyChallengeViewModel
import com.github.studydistractor.sdp.distraction.DistractionViewModel
import com.github.studydistractor.sdp.distractionList.DistractionListServiceFirebase
import com.github.studydistractor.sdp.distractionList.DistractionListViewModel
import com.github.studydistractor.sdp.distractionStat.DistractionStatServiceFirebase
import com.github.studydistractor.sdp.distractionStat.DistractionStatViewModel
import com.github.studydistractor.sdp.event.EventServiceFirebase
import com.github.studydistractor.sdp.event.EventViewModel
import com.github.studydistractor.sdp.eventChat.EventChatMiddlewareOffline
import com.github.studydistractor.sdp.eventChat.EventChatServiceFirebase
import com.github.studydistractor.sdp.eventChat.EventChatViewModel
import com.github.studydistractor.sdp.eventHistory.EventHistoryServiceFirebase
import com.github.studydistractor.sdp.eventHistory.EventHistoryViewModel
import com.github.studydistractor.sdp.eventList.EventListServiceFirebase
import com.github.studydistractor.sdp.eventList.EventListViewModel
import com.github.studydistractor.sdp.friends.FriendsViewModel
import com.github.studydistractor.sdp.history.HistoryServiceFirebase
import com.github.studydistractor.sdp.history.HistoryViewModel
import com.github.studydistractor.sdp.login.LoginServiceFirebase
import com.github.studydistractor.sdp.login.LoginViewModel
import com.github.studydistractor.sdp.maps.MapsActivity
import com.github.studydistractor.sdp.register.RegisterServiceFirebase
import com.github.studydistractor.sdp.register.RegisterViewModel
import com.github.studydistractor.sdp.roomdb.RoomDatabase
import com.github.studydistractor.sdp.ui.*
import com.google.firebase.auth.FirebaseAuth

// Inspired by https://developer.android.com/codelabs/basic-android-kotlin-compose-navigation#7

/**
 * enum values that represent the screens in the app
 */
enum class StudyDistractorScreen(@StringRes val title: Int) {
    Login(title = R.string.screen_name_login),
    Register(title = R.string.screen_name_register),
    Maps(title = R.string.screen_name_maps),
    Friends(title = R.string.screen_name_friends),
    DistractionList(title = R.string.screen_name_distraction_list),
    Distraction(title = R.string.screen_name_distraction),
    DistractionStat(title = R.string.screen_name_distraction_stat),
    CreateDistraction(title = R.string.screen_name_create_distraction),
    History(title = R.string.screen_name_history),
    CreateUser(title = R.string.screen_name_create_user),
    DailyChallenge(title = R.string.screen_name_daily_challenge),
    CreateEvent(title = R.string.screen_name_create_event),
    ChatEvent(title = R.string.screen_name_chat_event),
    EventHistory(title = R.string.screen_name_event_history),
    Event(title = R.string.screen_name_event),
    EventList(title = R.string.screen_name_event_list)
}

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

    val createDistractionViewModel =
        remember { CreateDistractionViewModel(CreateDistractionServiceFirebase()) }
    val createUserViewModel        =
        remember { CreateUserViewModel(CreateUserServiceFirebase()) }
    val friendsViewModel           =
        remember { FriendsViewModel(FriendsServiceFirebase()) }
    val historyViewModel           =
        remember { HistoryViewModel(HistoryServiceFirebase()) }
    val loginViewModel             =
        remember { LoginViewModel(LoginServiceFirebase()) }
    val registerViewModel          =
        remember { RegisterViewModel(RegisterServiceFirebase()) }
    val distractionViewModel       =
        remember { DistractionViewModel() }
    val distractionListViewModel   =
        remember { DistractionListViewModel(DistractionListServiceFirebase()) }
    val distractionStatViewModel          =
        remember { DistractionStatViewModel(DistractionStatServiceFirebase()) }
    val dailyChallengeViewModel =
        remember { DailyChallengeViewModel(DailyChallengeServiceFirebase()) }
    val eventViewModel =
        remember { EventViewModel(EventServiceFirebase()) }
    val chatEventViewModel =
        remember { EventChatViewModel(EventChatMiddlewareOffline(
            Room.databaseBuilder(
                context,
                RoomDatabase::class.java,
                "study-distractor-db"
            ).build(),
            EventChatServiceFirebase()
        )) }
    val eventListViewModel =
        remember { EventListViewModel(EventListServiceFirebase()) }
    val eventHistoryViewModel =
        remember { EventHistoryViewModel(EventHistoryServiceFirebase())}
    val createEventViewModel =
        remember { CreateEventViewModel(CreateEventServiceFirebase()) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
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
            },
            goToCreateEventActivity = {
                navController.navigate(StudyDistractorScreen.CreateEvent.name)
            },
        ) },
        bottomBar = { AppBarBottom(
            onHomeClick = { navController.navigate(StudyDistractorScreen.Login.name) },
            onListClick = { navController.navigate(StudyDistractorScreen.DistractionList.name) },
            onMapClick = { navController.navigate(StudyDistractorScreen.Maps.name) },
            onFriendsClick = { navController.navigate(StudyDistractorScreen.Friends.name) },
            onEventListClick = { navController.navigate(StudyDistractorScreen.EventList.name) },
            onEventHistoryClick = {navController.navigate(StudyDistractorScreen.EventHistory.name)},
            onMagicClick = { navController.navigate(StudyDistractorScreen.DailyChallenge.name) }
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
                        val uid = FirebaseAuth.getInstance().uid.orEmpty()
                        eventHistoryViewModel.setUserId(uid)
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
                DistractionScreen(distractionViewModel, onDistractionState = { it ->
                    distractionStatViewModel.updateDid(it)
                   navController.navigate(StudyDistractorScreen.DistractionStat.name)
                })
            }
            composable(route = StudyDistractorScreen.CreateDistraction.name)  {
                CreateDistractionScreen(
                    createDistractionViewModel = createDistractionViewModel,
                    onDistractionCreated = {
                        distractionListViewModel.allDistractions()
                        navController.navigate(StudyDistractorScreen.DistractionList.name)
                    }
                )
            }
            composable(route = StudyDistractorScreen.CreateEvent.name)  {
                CreateEventScreen(
                    createEventViewModel = createEventViewModel,
                    onEventCreated = {
                        Toast.makeText(context, "Event created", Toast.LENGTH_SHORT).show()
                    }
                )
            }
            composable(route = StudyDistractorScreen.History.name) {
                HistoryScreen(
                    historyViewModel = historyViewModel
                )
            }

            composable(route = StudyDistractorScreen.DailyChallenge.name) {
                dailyChallengeViewModel.updateDistractions() // reset to the current day
                DailyChallengeScreen(
                    dailyChallengeViewModel = dailyChallengeViewModel
                )
            }
            composable(route = StudyDistractorScreen.DistractionStat.name){
                DistractionStatScreen(
                    distractionStatViewModel,
                )
            }
            composable(route = StudyDistractorScreen.Friends.name){
                FriendsScreen(friendsViewModel)
            }
            composable(route = StudyDistractorScreen.Event.name) {
                EventScreen(
                    eventViewModel = eventViewModel,
                    onOpenChatClick = {
                        chatEventViewModel.changeEventChat(eventViewModel.uiState.value.eventId)
                        navController.navigate(StudyDistractorScreen.ChatEvent.name)
                    }
                )
            }
            composable(route = StudyDistractorScreen.ChatEvent.name){
                EventChatScreen(chatEventViewModel)
            }
            composable(route = StudyDistractorScreen.EventList.name) {
                EventListScreen(
                    onEventClicked = { event ->
                        eventViewModel.setEventId(event)
                        navController.navigate(StudyDistractorScreen.Event.name) },
                    eventListViewModel = eventListViewModel
                )
            }
            composable(route = StudyDistractorScreen.EventHistory.name){
                EventHistoryScreen(
                    eventHistoryViewModel = eventHistoryViewModel,
                    chatViewModel = chatEventViewModel,
                    onChatButtonClicked = {
                        navController.navigate(StudyDistractorScreen.ChatEvent.name)
                    }
                )
            }
        }
    }
}