package com.example.graduationproject_aos.screen.mypage

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun MyPageScreen(
    navController: NavHostController,
    bottomBarVisible: (Boolean) -> Unit,
) {
    Text(
        text = "MyPageScreen",
    )
}