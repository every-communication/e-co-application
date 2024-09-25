package com.example.graduationproject_aos

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.graduationproject_aos.screen.friend.FriendSceen
import com.example.graduationproject_aos.screen.friend.FriendViewModel
import com.example.graduationproject_aos.screen.home.HomeScreen
import com.example.graduationproject_aos.screen.login.LoginScreen
import com.example.graduationproject_aos.screen.login.LoginViewModel
import com.example.graduationproject_aos.screen.mypage.MyPageScreen
import com.example.graduationproject_aos.screen.signUp.SignUpScreen
import com.example.graduationproject_aos.screen.signUp.SignUpViewModel

sealed class Routes(val route: String) {
    data object Home : Routes("Home")
    data object Login : Routes("Login")
    data object SignUp : Routes("SignUp")
    data object Friend : Routes("Friend")
    data object MyPage : Routes("MyPage")
}

@Composable
fun NaviGraph(
    loginViewModel: LoginViewModel,
    signUpViewModel: SignUpViewModel,
    friendViewModel: FriendViewModel,
    navController: NavHostController,
    bottomBarVisible: (Boolean) -> Unit
) {
    val navStoreOwner = rememberViewModelStoreOwner()
    CompositionLocalProvider(
        LocalNavGraphViewModelStoreOwner provides navStoreOwner
    ) {
        NavHost(navController = navController, startDestination = Routes.Login.route) {

            composable(
                route = Routes.Login.route,
            ) {
                bottomBarVisible(false)
                LoginScreen(
                    navController = navController,
                    bottomBarVisible = bottomBarVisible,
                    loginViewModel = loginViewModel
                )
            }

            composable(
                route = Routes.Home.route,
            ) {
                bottomBarVisible(true)
                HomeScreen(
                    navController = navController,
                    bottomBarVisible = bottomBarVisible,
                )
            }

            composable(
                route = Routes.SignUp.route,
            ) {
                bottomBarVisible(false)
                SignUpScreen(
                    navController = navController,
                    bottomBarVisible = bottomBarVisible,
                    signUpViewModel = signUpViewModel
                )
            }

            composable(
                route = Routes.Friend.route,
            ) {
                bottomBarVisible(true)
                FriendSceen(
                    navController = navController,
                    bottomBarVisible = bottomBarVisible,
                    friendViewModel = friendViewModel
                )
            }

            composable(
                route = Routes.MyPage.route,
            ) {
                bottomBarVisible(true)
                MyPageScreen(
                    navController = navController,
                    bottomBarVisible = bottomBarVisible,
                )
            }
        }
    }
}