package com.example.graduationproject_aos.screen.home

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.graduationproject_aos.CallActivity
import com.example.graduationproject_aos.util.CustomStatusBar

@Composable
fun HomeScreen(
    navController: NavHostController,
    bottomBarVisible: (Boolean) -> Unit,
) {
    val context = LocalContext.current

    var userId by remember { mutableStateOf("") }
    var ipAddress by remember { mutableStateOf("") }

    Column {
        CustomStatusBar()
        Text(text = "HomeScreen")

        // userId 입력 필드
        TextField(
            value = userId,
            onValueChange = { userId = it },
            label = { Text("User ID") }
        )

        // ipAddress 입력 필드
        TextField(
            value = ipAddress,
            onValueChange = { ipAddress = it },
            label = { Text("IP Address") }
        )

        Spacer(modifier = Modifier.height(16.dp)) // 간격 추가

        // 버튼 클릭 시 입력된 값을 전달
        Button(onClick = {
            val intent = Intent(context, CallActivity::class.java).apply {
                putExtra("userId", userId)
                putExtra("ipAddress", ipAddress)
            }
            context.startActivity(intent)
        }) {
            Text(text = "Go to CallActivity")
        }
    }
}