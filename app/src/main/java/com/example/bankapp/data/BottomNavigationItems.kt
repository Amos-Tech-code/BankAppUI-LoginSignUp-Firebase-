package com.example.bankapp.data
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.StackedBarChart
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.StackedBarChart
import androidx.compose.ui.graphics.vector.ImageVector


data class BottomNavItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val hasNews: Boolean,
    val badges: Int
)

val bottomNavItems = listOf(
    BottomNavItem(
        title = "Home",
        selectedIcon = Icons.Filled.Home,
        unSelectedIcon = Icons.Outlined.Home,
        hasNews = false,
        badges = 0
    ),

    BottomNavItem(
        title = "Statistics",
        selectedIcon = Icons.Filled.StackedBarChart,
        unSelectedIcon = Icons.Outlined.StackedBarChart,
        hasNews = false,
        badges = 0
    ),

    BottomNavItem(
        title = "Cards",
        selectedIcon = Icons.Filled.CreditCard,
        unSelectedIcon = Icons.Outlined.CreditCard,
        hasNews = false,
        badges = 5
    ),

    BottomNavItem(
        title = "Profile",
        selectedIcon = Icons.Filled.AccountCircle,
        unSelectedIcon = Icons.Outlined.AccountCircle,
        hasNews = true,
        badges = 0
    ),
)
