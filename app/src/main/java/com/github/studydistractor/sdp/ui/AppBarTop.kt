package com.github.studydistractor.sdp.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview("App Bar Top")
fun AppBarTop() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                "Study Distractor",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = { println("navigation click") }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Localized description"
                )
            }
        },
//        actions = {
//            IconButton(onClick = { println("fav click") }) {
//                Icon(
//                    imageVector = Icons.Filled.Favorite,
//                    contentDescription = "Localized description"
//                )
//            }
//        }
    )
}