package com.pavdev.autocommit.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pavdev.autocommit.ui.theme.onBackgroundDark

@Composable
fun ContentError(error: String?, onNavigateToConfig: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(6.dp)
            .fillMaxSize(),
    ) {
        Text(
            error ?: "Unknown Disconnected Reasons",
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
        )
        Button(
            onClick = onNavigateToConfig,
            shape = RoundedCornerShape(4.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .width(200.dp)
                    .padding(horizontal = 20.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Build,
                    contentDescription = "Navigate",
                    tint = onBackgroundDark
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "CHECK YOUR \nSETTINGS",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 1.sp,
                        textAlign = TextAlign.Center,
                        color = onBackgroundDark
                    )
                )
            }
        }
    }
}
