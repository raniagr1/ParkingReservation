package com.example.myapplication.presentation.ui.screens

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lightspark.composeqr.QrCodeView

@Composable
fun QrCodePreview(data: String) {
    QrCodeView(
        data = data,
        modifier = Modifier.size(300.dp)
    )
}