package com.example.graduationproject_aos

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.graduationproject_aos.screen.login.LoginScreen
import com.example.graduationproject_aos.screen.login.LoginViewModel

sealed class Routes(val route: String) {
    data object Home : Routes("Home")
    data object Login : Routes("Login")
}

@Composable
fun NaviGraph(
    loginViewModel: LoginViewModel,
    navController: NavHostController,
//    bottomBarVisible: (Boolean) -> Unit
) {
    val navStoreOwner = rememberViewModelStoreOwner()
    CompositionLocalProvider(
        LocalNavGraphViewModelStoreOwner provides navStoreOwner
    ) {
        NavHost(navController = navController, startDestination = Routes.Login.route) {

            composable(
                route = Routes.Login.route,
            ) {
//                bottomBarVisible(false)
                LoginScreen(
                    navController = navController,
//                    bottomBarVisible = bottomBarVisible,
                    loginViewModel = loginViewModel
                )
            }
        }
    }
}