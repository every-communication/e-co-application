package com.example.graduationproject_aos.screen.mypage

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.graduationproject_aos.R
import com.example.graduationproject_aos.data.model.response.FriendList
import com.example.graduationproject_aos.data.model.response.MyInfo
import com.example.graduationproject_aos.data.model.response.ResponseGetFriendList
import com.example.graduationproject_aos.data.model.response.ResponseGetMyInfo
import com.example.graduationproject_aos.util.CustomStatusBar
import com.example.graduationproject_aos.util.UiState

@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
fun MyPageScreen(
    navController: NavHostController,
    bottomBarVisible: (Boolean) -> Unit,
    myPageViewModel: MyPageViewModel,
) {
    val lifecycleOwner = LocalLifecycleOwner
    val context = LocalContext.current
    var myInfo by remember { mutableStateOf(MyInfo(0, "", null, null, null, null)) }

    val uiState by myPageViewModel.getMyInfoState
        .flowWithLifecycle(lifecycleOwner.current.lifecycle)
        .collectAsState(initial = UiState.Empty)

    when (uiState) {
        is UiState.Empty -> Unit
        is UiState.Failure -> Unit
        is UiState.Loading -> Unit
        is UiState.Success -> {
            val data = (uiState as UiState.Success<ResponseGetMyInfo>).data
            myInfo = data.data
            myPageViewModel.resetMyInfoState()
        }
    }
    myPageViewModel.getMyInfo()
    Column {
        CustomStatusBar()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 18.dp, end = 18.dp)
                .background(Color.White),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.height(100.dp)
            ) {
                val painter = if (myInfo.thumbnail != null) {
                    rememberAsyncImagePainter(myInfo.thumbnail)
                } else {
                    painterResource(id = R.drawable.profile)
                }
                Image(
                    painter = painter,
                    contentDescription = null,
                    Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(20.dp))
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "내 정보",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.eco_pretendard_normal)),
                            fontSize = 14.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = myInfo.nickname ?: "Unknown",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.eco_pretendard_bold)),
                            fontSize = 16.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = myInfo.email ?: "Unknown",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.eco_pretendard_neutral)),
                            fontSize = 14.sp,
                            color = Color(
                                ContextCompat.getColor(
                                    context,
                                    R.color.assistive
                                ),
                            ),
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
            Text(
                text = "수정하기",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.eco_pretendard_normal)),
                    fontSize = 14.sp,
                    color = Color(
                        ContextCompat.getColor(
                            context,
                            R.color.assistive
                        ),
                    ),
                ),
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable {
                    }
            )
            Spacer(modifier = Modifier.height(10.dp))
            Divider(color = Color(ContextCompat.getColor(context, R.color.border)))
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "서비스 변경",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.eco_pretendard_normal)),
                    fontSize = 14.sp
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row {
                Box(
                    modifier = Modifier
                        .height(95.dp)
                        .weight(1f)
                        .padding(horizontal = 10.dp, vertical = 10.dp)
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                        .border(
                            width = 1.dp,
                            color = if (myInfo.userType == "DEAF") Color(
                                ContextCompat.getColor(
                                    context,
                                    R.color.primary
                                )
                            ) else Color.Gray,
                            shape = RoundedCornerShape(8.dp)
                        )
//                    .clickable { myInfo.userType = "DEAF" }
                ) {
                    if (myInfo.userType == "DEAF") {
                        Canvas(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(16.dp)
                        ) {
                            drawRoundRect(
                                color = Color(ContextCompat.getColor(context, R.color.primary)),
                                size = Size(16.dp.toPx(), size.height),
                                cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
                            )
                            drawRect(
                                color = Color(ContextCompat.getColor(context, R.color.primary)),
                                topLeft = Offset(8.dp.toPx(), 0f),
                                size = Size(8.dp.toPx(), size.height)
                            )
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(start = 24.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "농인이에요",
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.eco_pretendard_bold)),
                                fontSize = 14.sp
                            )
                        )
                        Text(
                            "수어 서비스가 필요해요",
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.eco_pretendard_normal)),
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .height(95.dp)
                        .weight(1f)
                        .padding(horizontal = 10.dp, vertical = 10.dp)
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                        .border(
                            width = 1.dp,
                            color = if (myInfo.userType == "NONDEAF") Color(
                                ContextCompat.getColor(
                                    context,
                                    R.color.primary
                                )
                            ) else Color.Gray,
                            shape = RoundedCornerShape(8.dp)
                        )
//                    .clickable { selectUserType = "NONDEAF" }
                ) {
                    if (myInfo.userType == "NONDEAF") {
                        Canvas(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(16.dp)
                        ) {
                            drawRoundRect(
                                color = Color(ContextCompat.getColor(context, R.color.primary)),
                                size = Size(16.dp.toPx(), size.height),
                                cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
                            )
                            drawRect(
                                color = Color(ContextCompat.getColor(context, R.color.primary)),
                                topLeft = Offset(8.dp.toPx(), 0f),
                                size = Size(8.dp.toPx(), size.height)
                            )
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(start = 24.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "청인이에요",
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.eco_pretendard_bold)),
                                fontSize = 14.sp
                            )
                        )
                        Text(
                            "수어 해석이 필요해요",
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.eco_pretendard_normal)),
                                fontSize = 12.sp
                            )
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Divider(color = Color(ContextCompat.getColor(context, R.color.border)))
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "로그 아웃",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.eco_pretendard_normal)),
                    fontSize = 14.sp
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            Divider(color = Color(ContextCompat.getColor(context, R.color.border)))
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "회원 탈퇴",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.eco_pretendard_normal)),
                    fontSize = 14.sp
                )
            )
        }
    }
}