package com.example.graduationproject_aos.screen.login

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavHostController
import com.example.graduationproject_aos.R
import com.example.graduationproject_aos.Routes
import com.example.graduationproject_aos.util.CustomButton
import com.example.graduationproject_aos.util.CustomOutlinedTextField
import com.example.graduationproject_aos.util.UiState
import com.example.graduationproject_aos.util.showToast

@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
fun LoginScreen(
    navController: NavHostController,
    bottomBarVisible: (Boolean) -> Unit,
    loginViewModel: LoginViewModel
) {
    var textId by remember { mutableStateOf("") }
    var textPw by remember { mutableStateOf("") }
    val lifecycleOwner = LocalLifecycleOwner
    val context = LocalContext.current
    val uiState by loginViewModel.postLoginUserState
        .flowWithLifecycle(
            lifecycle = lifecycleOwner.current.lifecycle,
            minActiveState = Lifecycle.State.CREATED
        )
        .collectAsState(initial = UiState.Empty)

    when (val state = uiState) {
        is UiState.Empty -> Unit
        is UiState.Failure -> {
            context.showToast(message = state.msg)
        }
        is UiState.Loading -> Unit
        is UiState.Success -> {
            bottomBarVisible(true)
            navController.navigate(Routes.Home.route) {
                popUpTo(Routes.Login.route) {
                    inclusive = true
                }
            }
            loginViewModel.resetLoginState()
        }
    }

    fun login(textId: String, textPw: String) {
        loginViewModel.postLoginUser(textId, textPw)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(id = R.drawable.ecologo), contentDescription = null)
        Spacer(modifier = Modifier.height(27.dp))
        Text(
            text = "로그인",
            color = Color.Black,
            fontSize = 14.sp,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 24.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        CustomOutlinedTextField(
            value = textId,
            onValueChange = { textId = it },
            placeholder = "이메일을 입력하세요.",
            trailingIcon = {
                IconButton(onClick = { textId = "" }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.icon_x),
                        contentDescription = "Clear text",
                        tint = Color(ContextCompat.getColor(context, R.color.assistive))
                    )
                }
            }
        )
        Spacer(modifier = Modifier.height(12.dp))
        CustomOutlinedTextField(
            value = textPw,
            onValueChange = { textPw = it },
            placeholder = "비밀번호를 입력하세요.",
            isPassword = true,
            trailingIcon = {
                IconButton(onClick = { textPw = "" }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.icon_x),
                        contentDescription = "Clear text",
                        tint = Color(ContextCompat.getColor(context, R.color.assistive))
                    )
                }
            }
        )
        Spacer(modifier = Modifier.height(12.dp))
        CustomButton(
            text = "로그인",
            backgroundColor = Color(ContextCompat.getColor(context, R.color.primary)),
            textColor = Color.White,
            padding = 24,
            onClick = { login(textId, textPw) }
        )
        Spacer(modifier = Modifier.height(12.dp))
        CustomButton(
            text = "카카오계정으로 로그인",
            backgroundColor = Color(ContextCompat.getColor(context, R.color.kakao)),
            textColor = Color.Black,
            padding = 24,
            onClick = { /* 카카오 로그인 버튼 클릭 시 동작 */ }
        )
        Spacer(modifier = Modifier.height(12.dp))
        CustomButton(
            text = "회원가입",
            backgroundColor = Color(ContextCompat.getColor(context, R.color.transparent)),
            textColor = Color.Black,
            padding = 24,
            onClick = { navController.navigate(Routes.SignUp.route) }
        )
    }
}
