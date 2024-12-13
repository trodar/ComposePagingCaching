package com.trodar.paging3compose.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


@Composable
fun backgroundColors():  Array<Pair<Float, Color>> = arrayOf(
    0.0f to MaterialTheme.colorScheme.primaryContainer,
    0.45f to MaterialTheme.colorScheme.secondaryContainer,
    0.55f to MaterialTheme.colorScheme.secondaryContainer,
    1f to MaterialTheme.colorScheme.primaryContainer,
)