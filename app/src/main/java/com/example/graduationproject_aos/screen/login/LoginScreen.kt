package com.example.graduationproject_aos.screen.login

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavHostController
import com.example.graduationproject_aos.R
import com.example.graduationproject_aos.ui.theme.GraduationProject_AOSTheme
import com.example.graduationproject_aos.util.CustomButton
import com.example.graduationproject_aos.util.UiState
import com.example.graduationproject_aos.util.showToast

@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
fun LoginScreen(
    navController: NavHostController,
//    bottomBarVisible: (Boolean) -> Unit,
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
//            bottomBarVisible(true)
//            navController.navigate(Routes.Map.route) {
//                popUpTo(Routes.Home.route) {
//                    inclusive = true
//                }
//            }
            context.showToast(message = "로그인 성공!")

        }
    }

    fun login(textId: String, textPw: String) {
        loginViewModel.postLoginUser(textId, textPw)
    }

    GraduationProject_AOSTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(painter = painterResource(id = R.drawable.ecologo), contentDescription = null)
            Spacer(modifier = Modifier.height(27.dp))
            Text(
                text = "로그인",
                style = TextStyle(fontSize = 14.sp),
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 24.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            TextField(
                value = textId,
                onValueChange = { textId = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .background(Color.White),
                label = { Text("이메일을 입력하세요.") },
                placeholder = { Text("") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = {
                    }
                )
            )
            Spacer(modifier = Modifier.height(12.dp))
            TextField(
                value = textPw,
                onValueChange = { textPw = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .background(Color.White),
                label = { Text("비밀번호를 입력하세요.") },
                placeholder = { Text("") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            Spacer(modifier = Modifier.height(12.dp))
            CustomButton(
                text = "로그인",
                backgroundColor = Color.Blue,
                textColor = Color.White,
                padding = 24,
                onClick = { login(textId, textPw) }
            )
            Spacer(modifier = Modifier.height(12.dp))
            CustomButton(
                text = "카카오계정으로 로그인",
                backgroundColor = Color.Yellow,
                textColor = Color.Black,
                padding = 24,
                onClick = { /* 카카오 로그인 버튼 클릭 시 동작 */ }
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "회원가입")
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun PreviewLoginScreen() {
//    LoginScreen(loginViewModel = LoginViewModel)
//}
