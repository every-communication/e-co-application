package com.example.graduationproject_aos.screen.signUp

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.TextStyle
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
fun SignUpScreen(
    navController: NavHostController,
    signUpViewModel: SignUpViewModel
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner
    var textEmail by remember { mutableStateOf("") }
    var textNickname by remember { mutableStateOf("") }
    var textPw by remember { mutableStateOf("") }
    var textPwCheck by remember { mutableStateOf("") }
    var selectUserType by remember { mutableStateOf("DEAF") }

    val uiState by signUpViewModel.postSignUpUserState
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
            navController.navigate(Routes.Login.route) {
                popUpTo(Routes.SignUp.route) {
                    inclusive = true
                }
            }
            signUpViewModel.resetSignUpState()
        }
    }

    fun signUp(email: String, pw: String, pwCheck: String, nickname: String, userType: String) {
        if (email.isEmpty()) {
            context.showToast("이메일을 입력해주세요")
            return
        }  else if (nickname.isEmpty()) {
            context.showToast("닉네임을 입력해주세요")
        }else if (pw.isEmpty()) {
            context.showToast("비밀번호를 입력해주세요")
        } else if (pwCheck.isEmpty()) {
            context.showToast("확인용 비밀번호를 입력해주세요")
        } else if (pw != pwCheck) {
            context.showToast("비밀번호가 일치하지 않습니다.")
        } else {
            signUpViewModel.postSignUpUser(email, pw, nickname, userType)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "회원가입",
//            style = TextStyle(fontFamily = FontFamily(Font(R.font.eco_pretendard_bold)), fontSize = 28.sp),
            style = TextStyle(fontSize = 28.sp),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(35.dp))
        Image(painter = painterResource(id = R.drawable.profile), contentDescription = null, Modifier.size(100.dp))
        Spacer(modifier = Modifier.height(19.dp))
        Text(
            text = "이메일 *",
            style = TextStyle(fontSize = 14.sp),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 24.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
        CustomOutlinedTextField(
            value = textEmail,
            onValueChange = { textEmail = it },
            placeholder = "이메일을 입력하세요.",
            trailingIcon = {
                IconButton(onClick = { textEmail = "" }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.icon_x),
                        contentDescription = "Clear text",
                        tint = Color(ContextCompat.getColor(context, R.color.assistive))
                    )
                }
            }
        )
        Spacer(modifier = Modifier.height(34.dp))
        Text(
            text = "닉네임 *",
            style = TextStyle(fontSize = 14.sp),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 24.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
        CustomOutlinedTextField(
            value = textNickname,
            onValueChange = { textNickname = it },
            placeholder = "닉네임을 입력하세요.",
            trailingIcon = {
                IconButton(onClick = { textNickname = "" }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.icon_x),
                        contentDescription = "Clear text",
                        tint = Color(ContextCompat.getColor(context, R.color.assistive))
                    )
                }
            }
        )
        Spacer(modifier = Modifier.height(34.dp))
        Text(
            text = "비밀번호 *",
            style = TextStyle(fontSize = 14.sp),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 24.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
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
        Spacer(modifier = Modifier.height(34.dp))
        Text(
            text = "비밀번호 확인 *",
            style = TextStyle(fontSize = 14.sp),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 24.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
        CustomOutlinedTextField(
            value = textPwCheck,
            onValueChange = { textPwCheck = it },
            placeholder = "비밀번호와 동일하게 입력하세요.",
            isPassword = true,
            trailingIcon = {
                IconButton(onClick = { textPwCheck = "" }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.icon_x),
                        contentDescription = "Clear text",
                        tint = Color(ContextCompat.getColor(context, R.color.assistive))
                    )
                }
            }
        )
        Spacer(modifier = Modifier.height(34.dp))
        Text(
            text = "농인 / 청인 *",
            style = TextStyle(fontSize = 14.sp),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 24.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))

        CustomButton(
            text = "회원가입 하기",
            backgroundColor = Color(ContextCompat.getColor(context, R.color.primary)),
            textColor = Color.White,
            padding = 24,
            onClick = { signUp(textEmail, textPw,textPwCheck, textNickname, selectUserType) }
        )
        Spacer(modifier = Modifier.height(12.dp))
    }
}

