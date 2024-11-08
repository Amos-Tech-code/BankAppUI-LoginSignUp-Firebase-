package com.example.bankapp.ui

import GetStartedScreen
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bankapp.R
import com.example.bankapp.data.ScreenNavigation
import com.example.bankapp.data.bottomNavItems


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(
    authViewModel: AuthViewModel,
    context: Context,
) {
    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val navController = rememberNavController()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            // Only display the BottomNavigationBar if the current route is not GetStarted, Login or SignUp Screen
            if (currentRoute != ScreenNavigation.GetStartedScreen.route && currentRoute !=
                ScreenNavigation.LoginScreen.route && currentRoute != ScreenNavigation.SignUpScreen.route)
            {
                BottomNavigationBar(navController)
            }
        },

        topBar = {
            TopAppBar(
                navController = navController,
                scrollBehavior = scrollBehaviour,
                authViewModel = authViewModel,
                context = context,
            )
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = ScreenNavigation.HomeScreen.route,
            modifier = Modifier
                .padding(innerPadding)
                .nestedScroll(scrollBehaviour.nestedScrollConnection),
            enterTransition = { slideInHorizontally { it } },
            exitTransition = { slideOutHorizontally { -it } },
            popEnterTransition = { slideInHorizontally { -it } },
            popExitTransition = { slideOutHorizontally { it } }

        ) {

            composable(ScreenNavigation.HomeScreen.route) {
                HomeScreen()
            }
            composable(ScreenNavigation.StatisticsScreen.route) {
                StatisticsScreen()
            }
            composable(ScreenNavigation.CardsScreen.route) {
                CardsScreen()
            }
            composable(ScreenNavigation.ProfileScreen.route) {
                ProfileScreen(
                    navController = navController,
                    authViewModel = authViewModel,
                    context = context
                    )
            }
            composable(ScreenNavigation.GetStartedScreen.route) {
                GetStartedScreen(
                    navController = navController
                )
            }
            composable(ScreenNavigation.LoginScreen.route) {
                LoginScreen(
                    navController = navController,
                    authViewModel = authViewModel
                )
            }
            composable(ScreenNavigation.SignUpScreen.route) {
                SignUpScreen(
                    navController = navController,
                    authViewModel = authViewModel
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    navController: NavController,
    scrollBehavior: TopAppBarScrollBehavior,
    authViewModel: AuthViewModel,
    context: Context,
) {

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    val screenTitle: @Composable () -> Unit = when (currentRoute) {
        ScreenNavigation.HomeScreen.route -> {
            {
                HomeScreenTopBar(
                    authViewModel = authViewModel,
                    context = context
                )
            }
        }
        ScreenNavigation.StatisticsScreen.route -> {
            { Text(text = ScreenNavigation.StatisticsScreen.title ) }
        }
        ScreenNavigation.CardsScreen.route -> {
            { Text(text = ScreenNavigation.CardsScreen.title ) }
        }
        ScreenNavigation.ProfileScreen.route -> {
            { ProfileScreenTopBar(authViewModel = authViewModel, context = context) }
        }
        else -> {
            { Text(text = stringResource(id = R.string.app_name) ) }
        }
    }

    val canNavigateBack = navController.previousBackStackEntry != null


    CenterAlignedTopAppBar(
        /*colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.6f)

        ),*/
        navigationIcon = {
            if (canNavigateBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .padding(start = 8.dp)
                        .clickable {
                            navController.popBackStack()
                        }
                )
            }
        },
        title = {
            screenTitle()
        },
        actions = {
        },
        scrollBehavior = scrollBehavior
    )
}



@Composable
fun BottomNavigationBar(navController: NavController) {
    var selected by remember {
        mutableIntStateOf(0)
    }
    // Get the current route from the NavController
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    // Update selected index based on the current route
    selected = when (currentRoute) {
        ScreenNavigation.HomeScreen.route -> 0
        ScreenNavigation.StatisticsScreen.route -> 1
        ScreenNavigation.CardsScreen.route -> 2
        ScreenNavigation.ProfileScreen.route -> 3
        else -> selected
    }

    NavigationBar(
        modifier = Modifier.height(105.dp)
    ) {
        bottomNavItems.forEachIndexed{ index, bottomNavItem ->
            NavigationBarItem(
                selected = index == selected,
                onClick = {
                    selected = index
                    /*TODO*/
                    when (index) {
                        0 -> navController.navigate(ScreenNavigation.HomeScreen.route){
                            popUpTo(ScreenNavigation.HomeScreen.route) { inclusive = true }
                        }
                        1 -> navController.navigate(ScreenNavigation.StatisticsScreen.route)
                        2 -> navController.navigate(ScreenNavigation.CardsScreen.route)
                        3 -> navController.navigate(ScreenNavigation.ProfileScreen.route)
                    }
                },
                icon = {
                    BadgedBox(
                        badge = {
                            if (bottomNavItem.badges != 0 ) {
                                Badge{
                                    Text(
                                        text = bottomNavItem.badges.toString()
                                    )
                                }
                            } else if (bottomNavItem.hasNews) {
                                Badge()
                            }
                        }
                    ) {
                        Icon(
                            imageVector =
                            if (index == selected) {
                                bottomNavItem.selectedIcon
                            } else {
                                bottomNavItem.unSelectedIcon
                            },
                            contentDescription = bottomNavItem.title
                        )
                    }
                },
                label = {
                    Text(
                        text = bottomNavItem.title
                    )
                }
            )
        }
    }
}
