package com.example.graduationproject_aos.screen.home

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.core.content.ContextCompat
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

    val permissions = listOf(
        Manifest.permission.INTERNET,
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.CAMERA,
        Manifest.permission.MODIFY_AUDIO_SETTINGS
    )

    // 권한 요청 런처 설정
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsGranted ->
        // 모든 권한이 승인되면 CallActivity로 이동
        if (permissionsGranted.all { it.value }) {
            goToCallActivity(context, userId, ipAddress)
        } else {
            println("Some permissions were denied.")
        }
    }

    // 버튼 클릭 시 권한 요청 함수
    fun requestPermissions() {
        // 권한이 이미 모두 승인되었는지 확인
        val allGranted = permissions.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
        if (allGranted) {
            goToCallActivity(context, userId, ipAddress)
        } else {
            permissionLauncher.launch(permissions.toTypedArray())
        }
    }

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
            requestPermissions()
        }) {
            Text(text = "Go to CallActivity")
        }
    }
}

fun goToCallActivity(context: Context, userId: String, ipAddress: String) {
    val intent = Intent(context, CallActivity::class.java).apply {
        putExtra("userId", userId)
        putExtra("ipAddress", ipAddress)
    }
    context.startActivity(intent)
}