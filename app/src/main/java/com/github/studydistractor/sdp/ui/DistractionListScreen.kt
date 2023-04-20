package com.github.studydistractor.sdp.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.studydistractor.sdp.distraction.Distraction
import com.github.studydistractor.sdp.distraction.DistractionListViewModel
import com.github.studydistractor.sdp.distraction.DistractionViewModel

/**
 * Screens that represent a list of scrollable distraction that we can then filter
 */
@Composable
fun DistractionListScreen(
    onClickingDistraction: () -> Unit = {},
    distractionViewModel: DistractionViewModel,
    distractionListViewModel: DistractionListViewModel
) {
    distractionListViewModel.allDistractions()
    val distractions = distractionListViewModel.distractions
    Column() {
        FilterPanel(distractionListViewModel)
        LazyColumn(){
            items(distractions) {distraction->
                DistractionLayout(distraction, onClickingDistraction, distractionViewModel)
            }
        }
    }
}

/**
 * layout to display a distraction in the list
 */
@Composable
fun DistractionLayout(activity : Distraction,
                      onClickingDistraction : () -> Unit = {},
                      distractionViewModel : DistractionViewModel) {
    Column {
        Box(
            modifier = Modifier
                .clickable {
                    Log.d("box", "detect click")
                    distractionViewModel.addDistraction(activity)
                    onClickingDistraction()
                }
                .testTag("distraction-list-screen__box-distraction")
        ) {
            Row(
                modifier = Modifier
                    .padding(all = 14.dp)
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Text(
                    text = activity.name!!,
                    style = MaterialTheme.typography.subtitle2,
                    modifier = Modifier.testTag("name")
                )
            }
        }
        Divider(
            modifier = Modifier.padding(horizontal = 14.dp),
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.08f)
        )
    }
}

/**
 * layout to display the filter panel
 */
@Composable
fun FilterPanel(
    distractionListViewModel: DistractionListViewModel
) {
    var isExpanded by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Filters",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )
            IconButton(
                onClick = { isExpanded = !isExpanded },
                modifier = Modifier.testTag("distraction-list-screen__filter-Button")
            ) {
                Icon(
                    imageVector = if (isExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (isExpanded) "Collapse filters" else "Expand filters"
                )
            }
        }
        if (isExpanded) {
            val tags = listOf("Food", "Drink", "Sport")
            var selectedTags by remember { mutableStateOf(distractionListViewModel.filterTags) }
            val selectedLength = remember { mutableStateOf(distractionListViewModel.filterLength) }

            Column(
                modifier = Modifier.testTag("filterIsOpen")
            ){
                // Tags filter
                Row(modifier = Modifier.padding(horizontal = 16.dp).testTag("tags"),
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Tags",
                        style = MaterialTheme.typography.subtitle1,
                        modifier = Modifier.weight(1f)
                    )

                    LazyRow(modifier = Modifier.padding(horizontal = 16.dp)) {
                        items(tags) { tag ->
                            val selected = selectedTags.contains(tag)
                            Button(
                                onClick = {
                                    if (selected) {
                                        selectedTags -= tag
                                    } else {
                                        selectedTags += tag
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (selected) MaterialTheme.colors.primary else MaterialTheme.colors.surface
                                ),
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier.padding(end = 8.dp).testTag("distraction-list-screen__button-select-tag")
                            ) {
                                Text(
                                    tag,
                                    color = if (selected) Color.White else MaterialTheme.colors.onSurface
                                )
                            }
                        }
                    }
                }

                Divider(
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.1f),
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                // Length filter
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp).testTag("length"),
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Length",
                        style = MaterialTheme.typography.subtitle1,
                        modifier = Modifier.weight(1f)
                    )
                    LengthButton("Short", Distraction.Length.SHORT, selectedLength) {
                        if(selectedLength.value == Distraction.Length.SHORT) {
                            selectedLength.value = null
                        } else {
                            selectedLength.value = it
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    LengthButton("Medium", Distraction.Length.MEDIUM, selectedLength) {
                        if(selectedLength.value == Distraction.Length.MEDIUM) {
                            selectedLength.value = null
                        } else {
                            selectedLength.value = it
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    LengthButton("Long", Distraction.Length.LONG, selectedLength) {
                        if(selectedLength.value == Distraction.Length.LONG) {
                            selectedLength.value = null
                        } else {
                            selectedLength.value = it
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // Apply button
                Button(
                    onClick = {
                        isExpanded = false
                        filterHelper(selectedLength.value, selectedTags, distractionListViewModel)
                              },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .testTag("distraction-list-screen__button-apply-button"),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Apply Filters", color = Color.White)
                }
            }
        }
    }
}

/**
 * layout to display the button to select the length
 */
@Composable
fun LengthButton(
    text: String,
    length: Distraction.Length,
    selectedLength: MutableState<Distraction.Length?>,
    onClick: (Distraction.Length) -> Unit
) {
    val selected = selectedLength.value == length
    Button(
        onClick = { onClick(length) },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) MaterialTheme.colors.primary else MaterialTheme.colors.surface
        ),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.widthIn(max = 120.dp).testTag("distraction-list-screen__select-length")
    ) {
        Text(text, color = if (selected) Color.White else MaterialTheme.colors.onSurface)
    }
}

//helper function to update the distractionListViewModel
fun filterHelper(length: Distraction.Length?, tags: Set<String>, distractionListViewModel: DistractionListViewModel)  {
    distractionListViewModel.filterLength = length
    distractionListViewModel.filterTags = tags
    distractionListViewModel.filterDistractions()
}