package com.github.studydistractor.sdp.history

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.runtime.*
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject


@AndroidEntryPoint(AppCompatActivity::class)
class HistoryActivity : Hilt_HistoryActivity() {

    /**
     * Interface for getting history entries
     */
    @Inject
    lateinit var hi : HistoryInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var historyEntries = hi.getHistory(hi.getCurrentUid()!!)
        setContent{
                HistoryScreen(historyEntries)
        }
    }

    /**
     * Load each card for each history entry
     */
    @Composable
    private fun HistoryScreen(historyEntries : MutableList<HistoryEntry>){
        LazyColumn(){
            items(historyEntries) {i->
                MessageCard(i)
            }
        }
    }

    /**
     * Create a card for the entry
     */
    @SuppressLint("SimpleDateFormat")
    @Composable
    private fun MessageCard(entry: HistoryEntry) {

        Row(modifier = Modifier.padding(all = 8.dp)) {
            var isExpanded by remember { mutableStateOf(false) }

            Column(
                modifier = Modifier.clickable { isExpanded = !isExpanded }
                    .testTag("entry"+entry.date)

            ) {
                Text(
                    text = entry.name,
                    color = MaterialTheme.colors.secondaryVariant,
                    style = MaterialTheme.typography.subtitle2
                )
                Text(
                    text = SimpleDateFormat("hh:mm MM/dd/yy").format(Date(entry.date)),
                    color = MaterialTheme.colors.secondaryVariant,
                    style = MaterialTheme.typography.subtitle2
                )
                Spacer(modifier = Modifier.height(4.dp))

                Surface(
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.animateContentSize().padding(1.dp),
                ) {
                    Text(
                        text = entry.description,
                        modifier = Modifier.padding(all = 4.dp),
                        maxLines = if(isExpanded) Int.MAX_VALUE else 1,
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }
    }

}