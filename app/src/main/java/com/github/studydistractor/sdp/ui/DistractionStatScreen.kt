package com.github.studydistractor.sdp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
        OutlinedTextField(
            value = feedback.value,
            onValueChange = { feedback.value = it },
            label = { Text("Add feedback") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email
            ),
            leadingIcon = {
                Icon(Icons.Filled.Forum, contentDescription = null)
            },
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth()
                .testTag("distraction-stat-screen__feedback-text-field")
        )
        Spacer(modifier = Modifier.height(4.dp))
        Button(
            onClick = {stat.postNewFeedback(did, uid, feedback.value)},
            Modifier.fillMaxWidth()
                .testTag("distraction-stat-screen__feedback-button")

        ) {
            Text("Validate")
        }



        Spacer(modifier = Modifier.height(4.dp))
        /**
         * Tags text field and button
         */
        OutlinedTextField(
            value = tag.value,
            onValueChange = { tag.value = it },
            label = { Text("Add tag") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email
            ),
            leadingIcon = {
                Icon(Icons.Filled.Tag, contentDescription = null)
            },
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth()
                .testTag("distraction-stat-screen__tag-text-field")
        )
        Spacer(modifier = Modifier.height(4.dp))

        Button(
            onClick = {stat.addTag(did,tag.value)},
            Modifier.testTag("distraction-stat-screen__tag-button")
                .fillMaxWidth()
        ) {
            Text("Validate")
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