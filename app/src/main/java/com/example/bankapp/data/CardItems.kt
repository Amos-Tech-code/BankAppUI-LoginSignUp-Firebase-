package com.example.bankapp.data

import androidx.compose.ui.graphics.vector.ImageVector

data class CardItems(
    val cardNumber: String,
    val name: String,
    val cardType: String,
    val date: String,
    val icon: ImageVector
)
