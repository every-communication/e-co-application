package com.example.graduationproject_aos

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    bottomBarVisible: Boolean
) {
    val context = LocalContext.current
    val screens = listOf(
        BottomNavItem.Friend, BottomNavItem.Home, BottomNavItem.MyPage
    )

    if (bottomBarVisible) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        NavigationBar(
            modifier = modifier,
            containerColor = colorResource(id = R.color.white),
        ) {
            screens.forEach { screen ->
                NavigationBarItem(
                    label = {
                        Text(
                            text = screen.title ?: "",
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.eco_pretendard_normal)),
                                fontSize = 14.sp,
                                color = if (currentRoute == screen.route) Color(
                                    ContextCompat.getColor(
                                        context,
                                        R.color.primary
                                    )
                                ) else Color(ContextCompat.getColor(context, R.color.black))
                            ),
                        )
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = screen.icon!!), contentDescription = "",
                            tint = if (currentRoute == screen.route) Color(
                                ContextCompat.getColor(
                                    context,
                                    R.color.primary
                                )
                            ) else Color(ContextCompat.getColor(context, R.color.black))
                        )
                    },
                    selected = currentRoute == screen.route,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(Routes.Home.route)
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.White,
                        unselectedTextColor = Color.Black,
                        selectedTextColor = Color.Black
                    ),
                )
            }
        }
    }
}

sealed class BottomNavItem(
    val route: String,
    val title: String? = null,
    val icon: Int? = null
) {
    object Friend : BottomNavItem(
        route = Routes.Friend.route,
        title = "친구목록",
        icon = R.drawable.friend
    )

    object Home : BottomNavItem(
        route = Routes.Home.route,
        title = "홈",
        icon = R.drawable.tab_call
    )

    object MyPage : BottomNavItem(
        route = Routes.MyPage.route,
        title = "마이페이지",
        icon = R.drawable.mypage
    )
}