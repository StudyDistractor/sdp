package com.github.studydistractor.sdp.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*

@Composable
fun DistractionListLengthButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.surface
        ),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .widthIn(max = 120.dp)
            .testTag("distraction-list-screen__select-length")
    ) {
        Text(text, color = if (isSelected) Color.White else MaterialTheme.colors.onSurface)
    }
}