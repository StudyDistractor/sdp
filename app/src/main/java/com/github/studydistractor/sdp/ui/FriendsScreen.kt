package com.github.studydistractor.sdp.ui

import android.widget.Toast
import androidx.compose.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.GroupAdd
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.studydistractor.sdp.friends.FriendsViewModel
import com.github.studydistractor.sdp.ui.components.MessageCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun  FriendsScreen(friendsViewModel: FriendsViewModel) {
    fun showFailureToast(context: Context, message: String) {
        Toast.makeText(context, "Failure: $message", Toast.LENGTH_SHORT)
            .show()
    }

    fun showSuccessToast(context: Context) {
        Toast.makeText(context, "Success", Toast.LENGTH_SHORT)
            .show()
    }

    val uiState by friendsViewModel.uiState.collectAsState()
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 32.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            OutlinedTextField(
                value = uiState.newFriend,
                onValueChange = { friendsViewModel.updateNewFriend(it) },
                label = { Text("Friend uid") },
                singleLine = true,
                leadingIcon = {
                    Icon(Icons.Filled.GroupAdd, contentDescription = null)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .testTag("friend-list-screen__friend-text-field")
            )
            Button(
                onClick = {
                    friendsViewModel.addFriend()
                        .addOnSuccessListener {
                            showSuccessToast(context)
                            friendsViewModel.refreshFriendsList()
                        }
                        .addOnFailureListener { showFailureToast(context, it.message.orEmpty()) }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("friend-list-screen__friend-button")
            ) {
                Text("Add new friend")
            }
            if (uiState.friendHistory.isNotEmpty()) {
                Text(
                    "Friend History : ",
                    fontSize = 20.sp,
                    modifier = Modifier.testTag("friend-list-screen__history-title")
                )
                LazyColumn() {
                    items(uiState.friendHistory) { historyEntry ->
                        MessageCard(entry = historyEntry)
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))
            LazyColumn() {
                items(uiState.friendsList) { friend ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillParentMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Uid : $friend",
                            fontSize = 10.sp,
                            modifier = Modifier
                                .padding(6.dp)
                                .align(Alignment.CenterVertically)
                                .testTag("friend-list-screen__friend-$friend")
                        )
                        IconButton(
                            onClick = {
                                friendsViewModel.refreshFriendHistory(friend)
                            },
                            modifier = Modifier
                                .testTag("friend-list-screen__history-$friend")
                                .padding(8.dp)
                        )
                        {
                            Icon(
                                imageVector = Icons.Default.History,
                                contentDescription = "history",
                                modifier = Modifier.align(Alignment.CenterVertically),
                                tint = Color.Blue
                            )
                        }
                        IconButton(
                            onClick = {
                                friendsViewModel.removeFriend(friend)
                                    .addOnSuccessListener {
                                        showSuccessToast(context)
                                        friendsViewModel.refreshFriendsList()
                                    }
                                    .addOnFailureListener {
                                        showFailureToast(
                                            context,
                                            it.message.orEmpty()
                                        )
                                    }
                            },
                            modifier = Modifier
                                .testTag("friend-list-screen__delete-$friend")
                                .padding(8.dp)
                        )
                        {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "delete",
                                modifier = Modifier.align(Alignment.CenterVertically),
                                tint = Color.Red
                            )
                        }
                    }
                    Spacer(
                        modifier = Modifier
                            .height(2.dp)
                            .background(Color.Gray)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}