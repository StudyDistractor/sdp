package com.github.studydistractor.sdp.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.github.studydistractor.sdp.dailyChallenge.DailyChallengeViewModel
import com.github.studydistractor.sdp.data.Distraction
import nl.dionsegijn.konfetti.compose.KonfettiView
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.util.concurrent.TimeUnit


@Composable
fun DailyChallengeScreen(
    dailyChallengeViewModel: DailyChallengeViewModel
) {
    val uiState by dailyChallengeViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("daily-challenge-screen__main-container"),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Text(
                text = "Complete these activities and earn the honour of being a master procrastinator.",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(vertical = 32.dp, horizontal = 16.dp)
                    .testTag("daily-challenge-screen__description")
            )
        }
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f, false),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            uiState.distractionsToDisplay.forEachIndexed { index, distraction ->
                DistractionCard(
                    distraction = distraction,
                    checkedState = uiState.checkedStates[index],
                    onCheckboxClicked = {
                        dailyChallengeViewModel.onCheckboxClicked(
                            index, it
                        )
                    }
                )
            }
        }
    }


    if (uiState.allChecked) {
        Log.d("DailyChallengeScreen", "all checked")
        KonfettiView(
            modifier = Modifier.fillMaxSize(),
            parties = listOf(
                Party(
                    emitter = Emitter(
                        duration = 5,
                        TimeUnit.SECONDS
                    ).perSecond(30)
                )
            ),
        )
    }
}

@Composable
fun DistractionCard(
    distraction: Distraction,
    checkedState: Boolean,
    onCheckboxClicked: (Boolean) -> Unit,
) {

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .testTag("distraction-card-for-${distraction.name}"),
            verticalAlignment = Alignment.Top
        ) {
            Checkbox(
                checked = checkedState,
                modifier = Modifier
                    .padding(top = 0.dp, bottom = 16.dp, start = 0.dp, end = 16.dp)
                    .testTag("distraction-card__checkbox-for-${distraction.name}"),
                onCheckedChange = onCheckboxClicked
            )
            Column {
                Row {
                    distraction.name?.let {
                        Text(
                            text = it,
                            modifier = Modifier.padding(
                                top = 0.dp,
                                bottom = 16.dp,
                                start = 0.dp,
                                end = 16.dp
                            ),
                            style = MaterialTheme.typography.headlineSmall,
                        )
                    }
                }
                Row {
                    distraction.description?.let {
                        Text(
                            text = it,
                            modifier = Modifier.padding(
                                top = 0.dp,
                                bottom = 16.dp,
                                start = 0.dp,
                                end = 16.dp
                            ),
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                }

            }
        }
    }
}
