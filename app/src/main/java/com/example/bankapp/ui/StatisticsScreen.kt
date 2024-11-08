package com.example.bankapp.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bankapp.ui.theme.DarkBlue


@Composable
fun StatisticsScreen(modifier: Modifier = Modifier){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.LightGray)
    ) {
        SpentOverview()
        Expenses()
        Transactions()
        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun SpentOverview() {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Spent Overview",
                fontSize = 21.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = "$10,576.00",
                fontSize = 27.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "10%",
                color = Color.Green,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)

                ) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color.Blue)
                            .size(10.dp)
                    )
                    Text(
                        text = "Investment",
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "$6,345.60"
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)

                ) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(DarkBlue)
                            .size(10.dp)
                    )
                    Text(
                        text = "Entertainment",
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "$3,172.80"
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color.Green)
                            .size(10.dp)
                    )
                    Text(
                        text = "Food & Beverages",
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "$1,057.60"
                    )
                }
            }
        }
        //parent Box
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Expenses() {

    var showDialog by rememberSaveable {
        mutableStateOf(false)
    }
    var selectedChart by rememberSaveable { mutableStateOf("This Week") }

    val thisWeekData = listOf(300f, 500f, 200f, 400f, 350f, 550f, 600f)
    val lastWeekData = listOf(250f, 400f, 350f, 500f, 300f, 450f, 350f)
    val thisMonthData = listOf(1000f, 800f, 900f, 1200f)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
    ) {

        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selectedChart,
                fontSize = 17.sp,
                modifier = Modifier.weight(1f)
            )

            IconButton(onClick = { showDialog = true }) {
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Choose Expenses Interval",
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Total Expenses",
                fontSize = 17.sp
            )
            Text(
                text = "$7,416.00",
                fontSize = 27.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "12.8%",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Red
            )
        }

       Box(
           modifier = Modifier
               .fillMaxWidth()
               .padding(16.dp)
               .clip(RoundedCornerShape(12.dp))
               .background(Color.LightGray)
       ) {
           Column(
               modifier = Modifier
                   .padding(16.dp)
           ) {
               Text(
                   text = "Expenses in Figure",
                   fontSize = 16.sp
               )
               Text(
                   text = "$7,416.00",
                   fontSize = 27.sp,
                   fontWeight = FontWeight.Bold
               )
           }

       }
        //GraphSection
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
           when (selectedChart) {
               "This Week" -> BarChart(data = thisWeekData, maxValue = 600f, xLabels = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"))
               "Last Week" -> BarChart(data = lastWeekData, maxValue = 600f, xLabels = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"))
               "This Month" -> BarChart(data = thisMonthData, maxValue = 1200f, xLabels = listOf("Week 1", "Week 2", "Week 3", "Week 4"))
           }
       }


        //Parent Column
    }


    if (showDialog) {
        BasicAlertDialog(
            onDismissRequest = { showDialog = false }
        ) {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(20.dp)
                    .clip(RoundedCornerShape(2.dp)),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    text = "This Week",
                    modifier = Modifier
                        .clickable {
                            selectedChart = "This Week"
                            showDialog = false
                        }
                )
                Text(
                    text = "Last Week",
                    modifier = Modifier
                        .clickable {
                            selectedChart = "Last Week"
                            showDialog = false
                        }
                )
                Text(
                    text = "This Month",
                    modifier = Modifier
                        .clickable {
                            selectedChart = "This Month"
                            showDialog = false
                        }
                )
            }
        }
    }

}



@Composable
fun BarChart(
    data: List<Float>,
    maxValue: Float,
    xLabels: List<String>,
    ySteps: Int = 6 // Number of steps on the y-axis
) {
    // Calculate the y-axis labels based on maxValue and number of steps
    val yLabels = (0..ySteps).map { step -> (step * maxValue / ySteps).toInt().toString() }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .padding(16.dp)
    ) {
        // Y Labels Column
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(end = 8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            yLabels.reversed().forEach { label ->
                Text(text = label, fontSize = 12.sp)
            }
        }

        // Chart with bars and axes
        Canvas(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            val barWidth = size.width / (data.size * 2)
            val chartHeight = size.height
            val chartWidth = size.width

            // Draw x-axis (horizontal line) in black
            drawLine(
                color = Color.Black,
                start = androidx.compose.ui.geometry.Offset(0f, chartHeight),
                end = androidx.compose.ui.geometry.Offset(chartWidth, chartHeight),
                strokeWidth = 2.dp.toPx()
            )

            // Draw y-axis (vertical line) in black
            drawLine(
                color = Color.Black,
                start = androidx.compose.ui.geometry.Offset(0f, 0f),
                end = androidx.compose.ui.geometry.Offset(0f, chartHeight),
                strokeWidth = 2.dp.toPx()
            )

            // Draw bars
            data.forEachIndexed { index, value ->
                val barHeight = chartHeight * (value / maxValue)
                drawRect(
                    color = Color.Blue,
                    topLeft = androidx.compose.ui.geometry.Offset(
                        x = index * barWidth * 2 + barWidth / 2,
                        y = chartHeight - barHeight
                    ),
                    size = androidx.compose.ui.geometry.Size(barWidth, barHeight)
                )
            }

            // Draw horizontal lines (grid)
            val stepHeight = chartHeight / ySteps
            for (i in 1..ySteps) {
                drawLine(
                    color = Color.LightGray,
                    start = androidx.compose.ui.geometry.Offset(0f, stepHeight * i),
                    end = androidx.compose.ui.geometry.Offset(chartWidth, stepHeight * i),
                    strokeWidth = 1.dp.toPx()
                )
            }
        }
    }

    // Display x-axis labels below the chart
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        xLabels.forEach { label ->
            Text(text = label, fontSize = 12.sp)
        }
    }
}



@Composable
fun Transactions() {
    Box(
        modifier = Modifier
            .padding(top = 12.dp, start = 8.dp, end = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
    ) {
        Column {
            Text(
                text = "Transactions",
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp)
            )
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.CreditCard,
                    contentDescription = null,
                    tint = DarkBlue,
                    modifier = Modifier.size(40.dp)
                )
                Column {
                    Text(
                        text = "Netflix Subscriptions",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 17.sp
                    )
                    Text(
                        text = "June 20, 2024 at 1:50 PM",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Box(modifier = Modifier.align(Alignment.CenterVertically)) {
                    Text(
                        text = "-$150.00",
                        color = Color.Red,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}