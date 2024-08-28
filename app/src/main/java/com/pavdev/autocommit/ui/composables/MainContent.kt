package com.pavdev.autocommit.ui.composables

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@Composable
fun MainContent(innerPadding: PaddingValues, content: String?) {
    var textSize by remember { mutableStateOf(16.sp) }
    val minTextSize = 2.sp
    val maxTextSize = 60.sp

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
    ) {

        Crossfade(
            label = "ContentCrossFade",
            targetState = content.orEmpty(),
            animationSpec = tween(1000)
        ) { animatedContent ->
            Text(
                text = animatedContent,
                fontSize = textSize,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState())
            )
        }

        Row (horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()){
            Slider(
                value = textSize.value,
                onValueChange = { textSize = it.sp },
                valueRange = minTextSize.value..maxTextSize.value,
                modifier = Modifier
                    .height(200.dp)
                    .width(200.dp)
                    .rotate(270f)
                    .offset(y = 80.dp).padding(end = 5.dp)

            )
        }


    }
}

