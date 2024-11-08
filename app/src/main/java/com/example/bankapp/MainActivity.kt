package com.example.bankapp

import GetStartedScreen
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bankapp.data.ScreenNavigation
import com.example.bankapp.ui.AppNavigation
import com.example.bankapp.ui.AuthViewModel
import com.example.bankapp.ui.LoginScreen
import com.example.bankapp.ui.SignUpScreen
import com.example.bankapp.ui.theme.BankAppTheme
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {

    private val authViewModel by viewModels<AuthViewModel>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BankAppTheme(darkTheme = false) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val context = LocalContext.current

                    BankApp(
                        modifier = Modifier
                            .padding(innerPadding),
                        authViewModel = authViewModel,
                        context = context,
                    )
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BankApp(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    context: Context,
) {

    val navController = rememberNavController()

    //NavHost
    NavHost(
        navController = navController,
        startDestination = ScreenNavigation.GetStartedScreen.route,
        modifier = Modifier
            .padding(),
        enterTransition = { slideInHorizontally { it } },
        exitTransition = { slideOutHorizontally { -it } },
        popEnterTransition = { slideInHorizontally { -it } },
        popExitTransition = { slideOutHorizontally { it } }

    ) {
        composable(ScreenNavigation.GetStartedScreen.route) {
            GetStartedScreen(navController = navController)
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
        composable(ScreenNavigation.HomeScreen.route) {
            AppNavigation(
                authViewModel = authViewModel,
                context = context,
            )
        }
    }

}