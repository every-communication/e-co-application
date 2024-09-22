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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    bottomBarVisible: Boolean
) {

//    val screens = listOf(
//        BottomNavItem.Item, BottomNavItem.Ranking,BottomNavItem.Ku
//    )

//    if (bottomBarVisible) {
//        val navBackStackEntry by navController.currentBackStackEntryAsState()
//        val currentRoute = navBackStackEntry?.destination?.route
//        NavigationBar(
//            modifier = modifier,
//            containerColor = colorResource(id = R.color.kuGreen),
//        ) {
//            screens.forEach { screen ->
//                NavigationBarItem(
//                    label = {
//                        Text(text = screen.title!!)
//                    },
//                    icon = {
//                        Icon(painter = painterResource(id = screen.icon!!), contentDescription = "")
//                    },
//                    selected = currentRoute == screen.route,
//                    onClick = {
//                        navController.navigate(screen.route){
//                            popUpTo(Routes.Map.route)
//                        }
//                    },
//                    colors = NavigationBarItemDefaults.colors(
//                        unselectedTextColor = Color.White, selectedTextColor = Color.White
//                    ),
//                )
//            }
//        }
//    }
}

//sealed class BottomNavItem(
//    val route: String,
//    val title: String? = null,
//    val icon: Int? = null
//) {
//    object Ku : BottomNavItem(
//        route = Routes.Ku.route,
//        title = "Ku",
//        icon = R.drawable.baseline_ku_24
//    )
//
//    object Ranking : BottomNavItem(
//        route = Routes.Ranking.route,
//        title = "Ranking",
//        icon = R.drawable.ic_navibar_ranking_2_24
//    )
//
//    object Item : BottomNavItem(
//        route = Routes.Item.route,
//        title = "Item",
//        icon = R.drawable.ic_navibar_bag_1_24
//    )
//}