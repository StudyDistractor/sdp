package com.github.studydistractor.sdp

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HourglassFull
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
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
import com.github.studydistractor.sdp.eventHistory.EventHistoryMiddlewareOffline
import com.github.studydistractor.sdp.eventList.EventListServiceFirebase
import com.github.studydistractor.sdp.eventList.EventListViewModel
import com.github.studydistractor.sdp.eventHistory.EventHistoryServiceFirebase
import com.github.studydistractor.sdp.eventHistory.EventHistoryViewModel
import com.github.studydistractor.sdp.eventList.EventListMiddlewareOffline
import com.github.studydistractor.sdp.friends.FriendsViewModel
import com.github.studydistractor.sdp.history.HistoryServiceFirebase
import com.github.studydistractor.sdp.history.HistoryViewModel
import com.github.studydistractor.sdp.login.LoginServiceFirebase
import com.github.studydistractor.sdp.login.LoginViewModel
import com.github.studydistractor.sdp.maps.MapViewModel
import com.github.studydistractor.sdp.register.RegisterServiceFirebase
import com.github.studydistractor.sdp.register.RegisterViewModel
import com.github.studydistractor.sdp.roomdb.RoomDatabase
import com.github.studydistractor.sdp.ui.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

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
        backStackEntry?.destination?.route ?: StudyDistractorScreen.DistractionList.name
    )
    val context = LocalContext.current
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val database = Room.databaseBuilder(
                context,
                RoomDatabase::class.java,
                "study-distractor-db"
            ).allowMainThreadQueries().build()

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
        remember { DistractionListViewModel(DistractionListServiceFirebase("ProcrastinationActivities", "Tags")) }
    val distractionStatViewModel          =
        remember { DistractionStatViewModel(DistractionStatServiceFirebase("Feedback", "Likes", "Dislikes", "TagsUsers")) }
    val dailyChallengeViewModel =
        remember { DailyChallengeViewModel(DailyChallengeServiceFirebase()) }
    val eventViewModel =
        remember { EventViewModel(EventServiceFirebase()) }
    val chatEventViewModel =
        remember { EventChatViewModel(EventChatMiddlewareOffline(
            database,
            EventChatServiceFirebase(),
            connectivityManager
        )) }
    val eventListViewModel =
        remember { EventListViewModel(
                EventListMiddlewareOffline(
                    EventListServiceFirebase("Events"),
                    database,
                    connectivityManager
                )
            )
        }
    val eventHistoryViewModel =
        remember { EventHistoryViewModel(
            EventHistoryMiddlewareOffline(
                EventHistoryServiceFirebase(),
                database,
                connectivityManager)
            )
        }
    val createEventViewModel =
        remember { CreateEventViewModel(CreateEventServiceFirebase("Events")) }
    val mapViewModel =
        remember{ MapViewModel(EventListServiceFirebase("Events"), DistractionListServiceFirebase("ProcrastinationActivities", "Tags"))}

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        modifier = Modifier.testTag("ModalNavigationDrawer"),
        gesturesEnabled = false,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = MaterialTheme.colorScheme.inverseOnSurface,
                modifier = Modifier.testTag("ModalDrawerSheet").pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        scope.launch {
                            if (drawerState.isOpen) {
                                drawerState.close()
                            }
                        }
                    })
                },
            ) {
                Spacer(Modifier.height(12.dp))
                Text(
                    text = "Study Distractor",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding).testTag("app-bar-top__title")
                )
                Divider(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(vertical = 12.dp)
                )

                NavigationDrawerItem(
                    icon = {Icon(
                        imageVector = Icons.Filled.HourglassFull,
                        contentDescription = "History"
                    )},
                    label = {Text("Distraction history")},
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate(StudyDistractorScreen.History.name)
                              },
                    selected = false,
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedContainerColor = MaterialTheme.colorScheme.inverseOnSurface
                    ),
                    modifier = Modifier
                        .padding(NavigationDrawerItemDefaults.ItemPadding)
                        .testTag("app-bar-top__history-button")
                )

                NavigationDrawerItem(
                    icon = {Icon(
                        Icons.Outlined.AccessTime,
                        contentDescription = "History of events",
                    )},
                    label = {Text("Event history")},
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate(StudyDistractorScreen.EventHistory.name)
                    },
                    selected = false,
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedContainerColor = MaterialTheme.colorScheme.inverseOnSurface
                    ),
                    modifier = Modifier
                        .padding(NavigationDrawerItemDefaults.ItemPadding)
                        .testTag("app-bar-top__event-history-button")
                )

                NavigationDrawerItem(
                    icon = {Icon(
                        Icons.Outlined.Group,
                        contentDescription = "List of friends",
                    )},
                    label = {Text("Friends")},
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate(StudyDistractorScreen.Friends.name)
                    },
                    selected = false,
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedContainerColor = MaterialTheme.colorScheme.inverseOnSurface
                    ),
                    modifier = Modifier
                        .padding(NavigationDrawerItemDefaults.ItemPadding)
                        .testTag("app-bar-top__friends-button")
                )

                NavigationDrawerItem(
                    icon = {Icon(
                        Icons.Outlined.AccountCircle,
                        contentDescription = "Home"
                    )},
                    label = {Text("Account")},
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate(StudyDistractorScreen.Login.name)
                    },
                    selected = false,
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedContainerColor = MaterialTheme.colorScheme.inverseOnSurface
                    ),
                    modifier = Modifier
                        .padding(NavigationDrawerItemDefaults.ItemPadding)
                        .testTag("app-bar-top__account-button")
                )

            }
        }
    ) {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.inverseOnSurface,
            topBar = { AppBarTop(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                openDrawer = { scope.launch { drawerState.open() }}
            ) },
            bottomBar = { AppBarBottom(
                onListClick = { navController.navigate(StudyDistractorScreen.DistractionList.name) },
                onMapClick = { navController.navigate(StudyDistractorScreen.Maps.name) },
                onEventListClick = { navController.navigate(StudyDistractorScreen.EventList.name) },
                onCreateDistractionActivityClick = {navController.navigate(StudyDistractorScreen.CreateDistraction.name)},
                onCreateEventActivityClick = {navController.navigate(StudyDistractorScreen.CreateEvent.name)},
                onMagicClick = { navController.navigate(StudyDistractorScreen.DailyChallenge.name) }
            ) }
        ) {
            NavHost(
                navController = navController,
                startDestination = StudyDistractorScreen.DistractionList.name,
                modifier = modifier
                    .padding(it).testTag("NavHost")
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
                        mapViewModel,
                        onDistractionClick = {distraction ->
                            distractionViewModel.updateDistraction(distraction)
                            navController.navigate(StudyDistractorScreen.Distraction.name)
                        },
                        onEventClick = {event ->
                            eventViewModel.setEventId(event)
                            navController.navigate(StudyDistractorScreen.Event.name)
                        }
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
}
