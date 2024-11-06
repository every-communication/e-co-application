package com.example.graduationproject_aos.screen.home

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavHostController
import com.example.graduationproject_aos.CallActivity
import com.example.graduationproject_aos.R
import com.example.graduationproject_aos.Routes
import com.example.graduationproject_aos.data.model.response.ResponseGetFriendList
import com.example.graduationproject_aos.data.model.response.ResponseRoomDto
import com.example.graduationproject_aos.screen.friend.ListItem
import com.example.graduationproject_aos.util.CustomOutlinedTextField
import com.example.graduationproject_aos.util.CustomStatusBar
import com.example.graduationproject_aos.util.UiState
import com.example.graduationproject_aos.util.showToast

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    bottomBarVisible: (Boolean) -> Unit,
    homeViewModel: HomeViewModel
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var code by remember { mutableStateOf("") }

    val permissions = listOf(
        Manifest.permission.INTERNET,
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.CAMERA,
        Manifest.permission.MODIFY_AUDIO_SETTINGS
    )

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsGranted ->
        if (permissionsGranted.all { it.value }) {
            goToCallActivity(context, code)
        } else {
            println("Some permissions were denied.")
        }
    }

    fun requestPermissions() {
        val allGranted = permissions.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
        if (allGranted) {
            goToCallActivity(context, code)
        } else {
            permissionLauncher.launch(permissions.toTypedArray())
        }
    }

    val uiState by homeViewModel.createRoomState
        .flowWithLifecycle(
            lifecycle = lifecycleOwner.lifecycle,
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
            bottomBarVisible(false)
            val data = (uiState as UiState.Success<ResponseRoomDto>).data
            code = data.data.code
            homeViewModel.resetCreateRoomState()
            goToCallActivity(context, code)
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize().background(Color.White),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { homeViewModel.createRoom() },
                containerColor = Color(ContextCompat.getColor(context, R.color.primary)),
                elevation = FloatingActionButtonDefaults.elevation(0.dp),
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.call),
                    contentDescription = "Call",
                    tint = Color.White,
                    modifier = Modifier
                        .size(27.dp)
                )
            }
        }
    ) {
        Column {
            CustomStatusBar()
            Spacer(modifier = Modifier.height(25.dp))
            Column(
                modifier = Modifier
                    .padding(horizontal = 18.dp)
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Text(
                    text = "방 접속하기",
                    color = Color.Black,
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.eco_pretendard_bold)),
                        fontSize = 16.sp
                    ),
                    modifier = Modifier
                        .align(Alignment.Start)
                )
                Spacer(modifier = Modifier.height(15.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = code,
                        onValueChange = { code = it },
                        placeholder = {
                            Text(
                                text = "코드를 입력하세요.",
                                style = TextStyle(
                                    fontFamily = FontFamily(Font(R.font.eco_pretendard_normal)),
                                    fontSize = 14.sp
                                ),
                                color = Color(
                                    ContextCompat.getColor(
                                        LocalContext.current,
                                        R.color.assistive
                                    )
                                ),
                                fontSize = 14.sp
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(52.dp)
                            .background(
                                Color(
                                    ContextCompat.getColor(
                                        LocalContext.current,
                                        R.color.transparent
                                    )
                                )
                            ),
                        textStyle = TextStyle(fontSize = 14.sp),
                        singleLine = true,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = Color.White,
                            cursorColor = Color.Black,
                            focusedBorderColor = Color(
                                ContextCompat.getColor(
                                    LocalContext.current,
                                    R.color.assistive
                                )
                            ),
                            unfocusedBorderColor = Color(
                                ContextCompat.getColor(
                                    LocalContext.current,
                                    R.color.assistive
                                )
                            )
                        ),
                        trailingIcon = {
                            IconButton(onClick = { code = "" }) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.icon_x),
                                    contentDescription = "Clear text",
                                    tint = Color(ContextCompat.getColor(context, R.color.assistive))
                                )
                            }
                        }
                    )
                    Text(
                        text = "접속",
                        color = Color(ContextCompat.getColor(context, R.color.primary)),
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.eco_pretendard_bold)),
                            fontSize = 16.sp
                        ),
                        modifier = Modifier
                            .clickable { requestPermissions() }
                            .align(Alignment.CenterVertically)
                    )
                }
                Spacer(modifier = Modifier.height(25.dp))
                Text(
                    text = "초대 목록 0건",
                    color = Color.Black,
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.eco_pretendard_bold)),
                        fontSize = 16.sp
                    ),
                    modifier = Modifier
                        .align(Alignment.Start)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            1.dp,
                            Color(ContextCompat.getColor(context, R.color.assistive)),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .height(170.dp)
                        .padding(10.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_call),
                            contentDescription = "Icon",
                            modifier = Modifier.size(80.dp)
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = "초대받은 내역이 없어요",
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.eco_pretendard_bold)),
                                fontSize = 16.sp
                            ),
                        )
                        Text(
                            text = "방 코드를 직접 입력해서 접속할 수 있어요",
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.eco_pretendard_normal)),
                                fontSize = 14.sp
                            ),
                            color = Color(
                                ContextCompat.getColor(
                                    LocalContext.current,
                                    R.color.assistive
                                )
                            ),
                        )
                    }
                }
                Spacer(modifier = Modifier.height(25.dp))
                Text(
                    text = "최근 통화 목록",
                    color = Color.Black,
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.eco_pretendard_bold)),
                        fontSize = 16.sp
                    ),
                    modifier = Modifier
                        .align(Alignment.Start)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            1.dp,
                            Color(ContextCompat.getColor(context, R.color.assistive)),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .height(170.dp)
                        .padding(10.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_call),
                            contentDescription = "Icon",
                            modifier = Modifier.size(80.dp)
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = "최근 통화 목록이 없어요",
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.eco_pretendard_bold)),
                                fontSize = 16.sp
                            ),
                        )
                        Text(
                            text = "방을 생성해서 통화를 시작해보세요",
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.eco_pretendard_normal)),
                                fontSize = 14.sp
                            ),
                            color = Color(
                                ContextCompat.getColor(
                                    LocalContext.current,
                                    R.color.assistive
                                )
                            ),
                        )
                    }
                }
            }
        }
    }
}

fun goToCallActivity(context: Context, code: String) {
    val intent = Intent(context, CallActivity::class.java).apply {
        putExtra("code", code)
    }
    context.startActivity(intent)
    Log.d("ABCD", code)
}