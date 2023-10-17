package com.example.nbatrivial.utils

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FullScreenLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier.width(200.dp),
        shape = RoundedCornerShape(20.dp),
        onClick = onClick
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(10.dp)
        )
    }
}

@Composable
fun CustomGameButton(
    text: String,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.outlinedButtonColors(containerColor = backgroundColor),
        onClick = onClick
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(10.dp),
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}
