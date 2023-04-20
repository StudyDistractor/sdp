package com.github.studydistractor.sdp.ui

import android.widget.Toast
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
fun  FriendListScreen(user : FriendsInterface) {
    val newFriend = remember { mutableStateOf(TextFieldValue("")) }
    val friends = user.fetchAllFriends(user.getCurrentUid()!!)
    val context = LocalContext.current
    Column() {
        Row(
            Modifier.padding(6.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            OutlinedTextField(
                value = newFriend.value,
                label = {Text("Friends uid") },
                onValueChange = { newFriend.value  = it },
                modifier = Modifier.testTag("friend-list-screen__friend-text-field")
            )
            IconButton(
                onClick = {
                    user.addNewFriend(
                        user.getCurrentUid()!!,
                        newFriend.value.text,
                        {
                            Toast.makeText(context, "Succeed", Toast.LENGTH_SHORT).show()
                        },
                        {
                            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
                        }
                    ) },
                modifier = Modifier.align(Alignment.CenterVertically)
                    .testTag("friend-list-screen__friend-button")
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "Add friend button",
                )
            }
        }
        LazyColumn(){
            items(friends) {i->
                Row(
                    modifier = Modifier.fillMaxWidth().fillParentMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Uid : $i",
                        modifier = Modifier.padding(6.dp)
                            .align(Alignment.CenterVertically)
                            .testTag("friend-list-screen__friend-$i")
                    )
                    IconButton(
                        onClick = {
                            user.removeFriend(user.getCurrentUid()!!, i,
                                {
                                    Toast.makeText(context, "Succeed", Toast.LENGTH_SHORT).show()
                                },
                                {
                                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
                                }
                            ) },
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
            }
        }
    }
}