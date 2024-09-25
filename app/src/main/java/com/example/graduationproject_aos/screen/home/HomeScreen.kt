package com.example.graduationproject_aos.screen.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(
    navController: NavHostController,
    bottomBarVisible: (Boolean) -> Unit,
) {
    Text(
        text = "HomeScreen",
    )
}