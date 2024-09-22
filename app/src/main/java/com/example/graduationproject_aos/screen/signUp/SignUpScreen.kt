package com.example.graduationproject_aos.screen.signUp

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.graduationproject_aos.R
import com.example.graduationproject_aos.ui.theme.GraduationProject_AOSTheme
import com.example.graduationproject_aos.util.CustomButton

@Composable
fun SignUpScreen(
) {
    var textId by remember { mutableStateOf("") }
    var textPw by remember { mutableStateOf("") }

    GraduationProject_AOSTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "회원가입",
                style = TextStyle(fontSize = 14.sp),
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 24.dp)
            )
            Spacer(modifier = Modifier.height(35.dp))
            Image(painter = painterResource(id = R.drawable.ecologo), contentDescription = null)
            Spacer(modifier = Modifier.height(19.dp))
            Text(
                text = "이메일 *",
                style = TextStyle(fontSize = 14.sp),
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 24.dp)
            )
            Spacer(modifier = Modifier.height(2.dp))
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
                        // Handle the action when the next button is pressed on the keyboard
                    }
                )
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
            TextField(
                value = textId,
                onValueChange = { textId = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .background(Color.White),
                label = { Text("닉네임을 입력하세요.") },
                placeholder = { Text("") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = {
                    }
                )
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
            Spacer(modifier = Modifier.height(34.dp))
            Text(
                text = "비밀번호 확인 *",
                style = TextStyle(fontSize = 14.sp),
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 24.dp)
            )
            Spacer(modifier = Modifier.height(2.dp))
            TextField(
                value = textPw,
                onValueChange = { textPw = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .background(Color.White),
                label = { Text("비밀번호와 동일하게 입력하세요.") },
                placeholder = { Text("") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            Spacer(modifier = Modifier.height(34.dp))
            Text(
                text = "농인 / 청인 *",
                style = TextStyle(fontSize = 14.sp),
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 24.dp)
            )
            CustomButton(
                text = "회원가입 하기",
                backgroundColor = Color.Blue,
                textColor = Color.White,
                padding = 24,
                onClick = { /* 로그인 버튼 클릭 시 동작 */ }
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    SignUpScreen()
}
