package com.example.graduationproject_aos.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.graduationproject_aos.util.CustomStatusBar

@Composable
fun HomeScreen(
    navController: NavHostController,
    bottomBarVisible: (Boolean) -> Unit,
) {
    Column {
        CustomStatusBar()
        Text(
            text = "HomeScreen",
        )
    }
}