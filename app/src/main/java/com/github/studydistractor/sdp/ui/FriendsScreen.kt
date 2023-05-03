package com.github.studydistractor.sdp.ui

import android.widget.Toast
import androidx.compose.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.github.studydistractor.sdp.account.FriendsInterface
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

    Column (modifier = Modifier.padding(20.dp)){
        Row(
            Modifier
                .padding(6.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            OutlinedTextField(
                value = uiState.newFriend,
                onValueChange = { newFriend.value = it },
                label = { Text("Friend uid") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email
                ),
                leadingIcon = {
                    Icon(Icons.Filled.GroupAdd, contentDescription = null)
                },
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .testTag("friend-list-screen__friend-text-field")
            )
            IconButton(
                onClick = {
                    friendsViewModel.addFriend()
                        .addOnSuccessListener { showSuccessToast(context) }
                        .addOnFailureListener { showFailureToast(context, it.message.orEmpty()) }
                    ) },
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .testTag("friend-list-screen__friend-button")
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "Add friend button",
                )
            }
        }
        if(friendsHistory.isNotEmpty()){
            Text(
                "Friend History : ",
                fontSize = 20.sp,
                modifier = Modifier.testTag("friend-list-screen__history-title")
            )
            LazyColumn(){
                items(friendsHistory){i ->
                    MessageCard(entry = i)
                }
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        LazyColumn(){
            items(uiState.friendsList) {i ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillParentMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Uid : $i",
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(6.dp)
                            .align(Alignment.CenterVertically)
                            .testTag("friend-list-screen__friend-$i")
                    )
                    IconButton(
                        onClick = {
                                  friend.fetchFriendHistory(i)
                        },
                        modifier = Modifier
                            .testTag("friend-list-screen__history-$i")
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
                            friendsViewModel.removeFriend(i)
                                .addOnSuccessListener { showSuccessToast(context) }
                                .addOnFailureListener {
                                    showFailureToast(
                                        context,
                                        it.message.orEmpty()
                                    )
                                  },
                        modifier = Modifier
                            .testTag("friend-list-screen__delete-$i")
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
                Spacer(modifier = Modifier
                    .height(2.dp)
                    .background(Color.Gray)
                    .fillMaxWidth())
            }
        }
    }
}