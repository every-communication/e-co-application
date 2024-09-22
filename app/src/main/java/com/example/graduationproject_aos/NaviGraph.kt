package com.example.graduationproject_aos

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.graduationproject_aos.screen.HomeScreen
import com.example.graduationproject_aos.screen.call.CallScreen
import com.example.graduationproject_aos.screen.login.LoginScreen
import com.example.graduationproject_aos.screen.login.LoginViewModel
import com.example.graduationproject_aos.screen.signUp.SignUpScreen
import com.example.graduationproject_aos.screen.signUp.SignUpViewModel

sealed class Routes(val route: String) {
    data object Home : Routes("Home")
    data object Login : Routes("Login")
    data object SignUp : Routes("SignUp")

}

@Composable
fun NaviGraph(
    loginViewModel: LoginViewModel,
    signUpViewModel: SignUpViewModel,
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

            composable(
                route = Routes.Home.route,
            ) {
//                bottomBarVisible(false)
                HomeScreen(
                    navController = navController,
//                    bottomBarVisible = bottomBarVisible,
//                    loginViewModel = loginViewModel
                )
            }

            composable(
                route = Routes.SignUp.route,
            ) {
//                bottomBarVisible(false)
                SignUpScreen(
                    navController = navController,
//                    bottomBarVisible = bottomBarVisible,
                    signUpViewModel = signUpViewModel
                )
            }
        }
    }
}