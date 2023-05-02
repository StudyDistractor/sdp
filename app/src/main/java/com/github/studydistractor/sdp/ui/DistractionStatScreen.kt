package com.github.studydistractor.sdp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.studydistractor.sdp.procrastinationActivity.DistractionStatInterface

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DistractionStatScreen(uid : String, did : String, stat : DistractionStatInterface) {
    val feedback = remember {mutableStateOf("")}
    val tag = remember {mutableStateOf("")}
    val likes = stat.fetchLikeCount(did)
    val dislikes = stat.fetchDislikeCount(did)
    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(26.dp),
//        verticalArrangement = Arrangement.SpaceAround,
//        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        /**
         * Like and dislike count
         */
        Row(
            horizontalArrangement =  Arrangement.Center,
            modifier = Modifier.fillMaxWidth()

        ){
            Text(text = "Likes : ${likes.value}", fontSize = 20.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Dislikes :${dislikes.value}", fontSize = 20.sp)
        }
        Spacer(modifier = Modifier.height(4.dp))
        /**
         * Like and dislike buttons
         */
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement =  Arrangement.Center
        ) {
            IconButton(
                onClick = { stat.postLike(did, uid) },
                Modifier.testTag("distraction-stat-screen__like")

            ) {
                Icon(imageVector = Icons.Default.ThumbUp, contentDescription = "Like")
            }

            IconButton(
                onClick = { stat.postDislike(did, uid) },
                Modifier.testTag("distraction-stat-screen__dislike")
            ) {
                Icon(imageVector = Icons.Default.ThumbDown, contentDescription = "Like")
            }
        }
        Spacer(modifier = Modifier.height(4.dp))

        /**
         * Feedback text field and button
         */
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement =  Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically

        ){
            OutlinedTextField(
                value = feedback.value,
                onValueChange = { feedback.value = it },
                label = { androidx.compose.material3.Text("Add feeback") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email
                ),
                leadingIcon = {
                    androidx.compose.material3.Icon(Icons.Filled.Forum, contentDescription = null)
                },
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .testTag("distraction-stat-screen__feedback-text-field")
            )
            IconButton(
                onClick = {stat.postNewFeedback(did, uid, feedback.value)},
                Modifier.testTag("distraction-stat-screen__feedback-button")

            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add feedback",
                )
            }

        }

        Spacer(modifier = Modifier.height(4.dp))
        /**
         * Tags text field and button
         */
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement =  Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            OutlinedTextField(
                value = tag.value,
                onValueChange = { tag.value = it },
                label = { androidx.compose.material3.Text("Add tag") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email
                ),
                leadingIcon = {
                    androidx.compose.material3.Icon(Icons.Filled.Tag, contentDescription = null)
                },
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .testTag("distraction-stat-screen__tag-text-field")
            )

            IconButton(
                onClick = {stat.addTag(did,tag.value)},
                Modifier.testTag("distraction-stat-screen__tag-button")
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add tag",
                )
            }

        }
        Spacer(modifier = Modifier.height(4.dp))
        /**
         * List of tags
         */
        Text("Feedbacks : ", fontSize = 24.sp)
        LazyColumn(){
            items(stat.fetchDistractionFeedback(did)) {i->
                Text(
                    "- $i",
                    fontSize = 20.sp,
                    modifier = Modifier.testTag("distraction-stat-screen__feedback-$i")
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        /**
         * List of feedbacks
         */
        Text("Tags: ", fontSize = 24.sp)
        LazyColumn(){
            items(stat.fetchDistractionTags(did)) {i->
                Text(
                    "- $i",
                    fontSize = 20.sp,
                    modifier =
                        Modifier.testTag("distraction-stat-screen__tag-$i"))
            }
        }


    }
}