package com.pavdev.autocommit.ui.composables.appbar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pavdev.autocommit.ui.theme.onBackgroundDark
import kotlinx.coroutines.delay

@Composable
fun ShaText(sha: String?) {
    var displayedText by remember { mutableStateOf("") }
    val targetText = sha.orEmpty()

    LaunchedEffect(targetText) {
        displayedText = ""
        targetText.forEach { char ->
            delay(10)
            displayedText += char
        }
    }
    Text(
        text = displayedText,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 3.dp),
        style = TextStyle(fontSize = 12.sp, color = onBackgroundDark),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        textAlign = TextAlign.Start
    )
}