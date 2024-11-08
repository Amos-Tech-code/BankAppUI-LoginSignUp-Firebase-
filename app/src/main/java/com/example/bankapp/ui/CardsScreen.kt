package com.example.bankapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bankapp.data.CardItems
import com.example.bankapp.ui.theme.BlueCenter


val cardItems = listOf(
    CardItems(
        cardNumber = "**** **** **** 1234",
        name = "Anthony Muhoro",
        cardType = "Visa",
        date = "12/24",
        icon = Icons.Outlined.Delete
    ),
    CardItems(
        cardNumber = "**** **** **** 5678",
        name = "Anthony Muhoro",
        cardType = "Master Card",
        date = "01/25",
        icon = Icons.Outlined.Delete
    ),
    CardItems(
        cardNumber = "**** **** **** 9012",
        name = "Anthony Muhoro",
        cardType = "American Express",
        date = "08/23",
        icon = Icons.Outlined.Delete
    ),
    CardItems(
        cardNumber = "**** **** **** 3456",
        name = "Anthony Muhoro",
        cardType = "Discover",
        date = "12/24",
        icon = Icons.Outlined.Delete
    ),
    CardItems(
        cardNumber = "**** **** **** 4561",
        name = "Anthony Muhoro",
        cardType = "Visa",
        date = "03/21",
        icon = Icons.Outlined.Delete
    )
)

@Composable
fun CardsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Content (LazyColumn with cards)
        LazyColumn(
            modifier = Modifier.weight(1f) // Make LazyColumn take the available space
        ) {
            items(cardItems.size) { index ->
                CardItem(index)
            }
        }

        // Button at the bottom
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                .clip(RoundedCornerShape(8.dp)),
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
            onClick = { }
        ) {
            Row(
                modifier = Modifier.padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.AddCircleOutline,
                    contentDescription = "Add New Card",
                    modifier = Modifier.size(30.dp)
                )
                Text(
                    text = "Add New Card",
                    fontSize = 17.sp
                )
            }
        }
    }
}


//@Composable
//fun Cards() {
//    LazyColumn(
//        modifier = Modifier
//
//    ) {
//        items(cardItems.size) { index ->
//            CardItem(index)
//        }
//    }
//}

@Composable
fun CardItem(index: Int) {
    val cards = cardItems[index]

    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(
                BlueCenter
            )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = cards.cardNumber,
                fontSize = 17.sp,
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = cards.name,
                    fontSize = 17.sp,
                    color = Color.White,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = cards.date,
                    fontSize = 17.sp,
                    color = Color.White,
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = cards.cardType,
                    fontSize = 17.sp,
                    color = Color.White,
                    modifier = Modifier.weight(1f)

                )
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.Red.copy(0.7f))
                        .padding(4.dp)
                ) {
                    Icon(
                        imageVector = cards.icon,
                        contentDescription = "Delete",
                        tint = Color.White
                    )
                }
            }

        }
    }
}

