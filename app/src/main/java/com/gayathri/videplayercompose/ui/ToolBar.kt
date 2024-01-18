package com.gayathri.videplayercompose.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ToolBar() {
    Row {
        Text(
            text = "Videos",
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(8.dp),
            color = Color.White
        )
    }
}
