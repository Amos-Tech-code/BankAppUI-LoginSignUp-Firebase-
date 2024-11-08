package com.example.bankapp.data

import kotlinx.serialization.Serializable

@Serializable
sealed class ScreenNavigation(val route: String, val title: String) {

    @Serializable
    data object GetStartedScreen : ScreenNavigation("get_started", "")

    @Serializable
    data object LoginScreen : ScreenNavigation("login_screen", "")

    @Serializable
    data object SignUpScreen : ScreenNavigation("sign_up", "")

    @Serializable
    data object HomeScreen : ScreenNavigation("home_screen", "")

    @Serializable
    data object StatisticsScreen : ScreenNavigation("statistics_screen", "Statistics")

    @Serializable
    data object CardsScreen : ScreenNavigation("cards_screen", "Cards")

    @Serializable
    data object ProfileScreen : ScreenNavigation("profile_screen", "Profile")
}
