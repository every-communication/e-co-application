package com.example.graduationproject_aos.screen.friend

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.graduationproject_aos.R
import com.example.graduationproject_aos.data.model.response.FriendList
import com.example.graduationproject_aos.data.model.response.ResponseGetFriendList
import com.example.graduationproject_aos.util.CustomStatusBar
import com.example.graduationproject_aos.util.UiState
import kotlinx.coroutines.launch
import androidx.lifecycle.flowWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.example.graduationproject_aos.data.model.response.FriendSearchList
import com.example.graduationproject_aos.data.model.response.ResponseSearchFriendList
import com.example.graduationproject_aos.util.CustomOutlinedTextField

@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
fun FriendSceen(
    navController: NavHostController,
    bottomBarVisible: (Boolean) -> Unit,
    friendViewModel: FriendViewModel
) {
    val tabs = listOf("내 친구 목록", "친구 요청", "친구 요청중")
    val pagerState = rememberPagerState {
        tabs.size
    }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var getFriendList by remember { mutableStateOf<List<FriendList>>(emptyList()) }
    var getSearchFriendList by remember { mutableStateOf<List<FriendSearchList>>(emptyList()) }
    val lifecycleOwner = LocalLifecycleOwner
    var currentPage by remember { mutableStateOf(0) }

    val uiState by friendViewModel.getFriendListState
        .flowWithLifecycle(lifecycleOwner.current.lifecycle)
        .collectAsState(initial = UiState.Empty)

    val uiSearchFriendState by friendViewModel.getSearchFriendListState
        .flowWithLifecycle(lifecycleOwner.current.lifecycle)
        .collectAsState(initial = UiState.Empty)

    LaunchedEffect(currentPage) {
        when (currentPage) {
            0 -> friendViewModel.getAllFriend()
            1 -> friendViewModel.getRequestedFriend()
            2 -> friendViewModel.getRequestFriend()
        }
    }

    fun mapper(value: ResponseGetFriendList): List<FriendList> {
        return value.data.map {
            FriendList(
                userId = it.userId,
                email = it.email,
                nickname = it.nickname,
                thumbnail = it.thumbnail,
            )
        }
    }

    fun searchmapper(value: ResponseSearchFriendList): List<FriendSearchList> {
        return value.data.map {
            FriendSearchList(
                userId = it.userId,
                email = it.email,
                nickname = it.nickname,
                thumbnail = it.thumbnail,
                friendType = it.friendType
            )
        }
    }

    when (uiState) {
        is UiState.Empty -> Unit
        is UiState.Failure -> Unit
        is UiState.Loading -> Unit
        is UiState.Success -> {
            val data = (uiState as UiState.Success<ResponseGetFriendList>).data
            getFriendList = mapper(data)
            friendViewModel.resetFriendListState()
        }
    }

    when (uiSearchFriendState) {
        is UiState.Empty -> Unit
        is UiState.Failure -> Unit
        is UiState.Loading -> Unit
        is UiState.Success -> {
            val data = (uiSearchFriendState as UiState.Success<ResponseSearchFriendList>).data
            getSearchFriendList = searchmapper(data)
            friendViewModel.resetSearchFriendListState()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CustomStatusBar()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 18.dp, end = 18.dp, top = 18.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TabRow(
                    selectedTabIndex = pagerState.currentPage,
                    indicator = {},
                    divider = {
                        Divider(
                            color = Color(
                                ContextCompat.getColor(
                                    context,
                                    R.color.border
                                )
                            )
                        )
                    },
                    containerColor = Color.White,
                    contentColor = Color.Black
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            text = {
                                Text(
                                    title,
                                    color = if (pagerState.currentPage == index) Color(
                                        ContextCompat.getColor(
                                            context,
                                            R.color.primary
                                        )
                                    ) else Color.Black
                                )
                            },
                            selected = pagerState.currentPage == index,
                            modifier = Modifier
                                .width(200.dp)
                                .height(50.dp),
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)
                                    currentPage = index
                                }
                            }
                        )
                    }
                }
                Box(modifier = Modifier.fillMaxSize()) {
                    HorizontalPager(
                        state = pagerState,
                        Modifier
                            .fillMaxSize()
                            .padding(vertical = 18.dp),
                        verticalAlignment = Alignment.Top
                    ) { page ->
                        FriendListScreen(page, getFriendList, friendViewModel, getSearchFriendList)
                    }
                }
            }
        }
    }
}

@Composable
fun FriendListScreen(
    page: Int,
    getFriendList: List<FriendList>,
    friendViewModel: FriendViewModel,
    getAllFriendList: List<FriendSearchList>
) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showDialog = true
                    friendViewModel.getSearchFriend("")
                },
                containerColor = Color.Transparent, // 배경 투명
                elevation = FloatingActionButtonDefaults.elevation(0.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.button),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .size(60.dp) // 원하는 크기 설정
                        .clip(CircleShape)
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    1.dp,
                    Color(ContextCompat.getColor(context, R.color.border)),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(10.dp)
                .clip(RoundedCornerShape(8.dp))
        ) {
            LazyColumn(
                contentPadding = paddingValues // Scaffold의 패딩을 적용
            ) {
                items(getFriendList) { friend ->
                    ListItem(page, friend, friendViewModel)
                }
            }
        }
        if (showDialog) {
            FriendSearchDialog(getAllFriendList, friendViewModel) {
                showDialog = false
            }
        }
    }
}


@Composable
fun ListItem(page: Int, friend: FriendList, friendViewModel: FriendViewModel) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            val painter = if (friend.thumbnail != null) {
                rememberAsyncImagePainter(friend.thumbnail)
            } else {
                painterResource(id = R.drawable.profile)
            }
            Image(
                painter = painter,
                contentDescription = null,
                Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Column {
                Text(
                    text = friend.nickname ?: "Unknown",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.eco_pretendard_normal)),
                        fontSize = 14.sp
                    )
                )
                Text(
                    text = friend.email ?: "Unknown",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.eco_pretendard_neutral)),
                        fontSize = 14.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
        when (page) {
            0 -> {
                Text(
                    text = "친구 끊기",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.eco_pretendard_bold)),
                        fontSize = 16.sp,
                        color = Color(ContextCompat.getColor(context, R.color.negative))
                    ),
                    modifier = Modifier
                        .width(60.dp)
                        .clickable {
                            //친구 끊기
                            friendViewModel.deleteFriend(friend.userId)
                        }
                )
            }

            1 -> {
                Row(
                    modifier = Modifier.width(65.dp)
                ) {
                    Text(
                        text = "수락",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.eco_pretendard_bold)),
                            fontSize = 16.sp,
                            color = Color(ContextCompat.getColor(context, R.color.positive))
                        ),
                        modifier = Modifier.clickable {
                            friendViewModel.postFriendRequestedApprove(friend.userId)
                        }
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "거절",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.eco_pretendard_bold)),
                            fontSize = 16.sp,
                            color = Color(ContextCompat.getColor(context, R.color.negative))
                        ),
                        modifier = Modifier.clickable {
                            friendViewModel.patchFriendRequestedRemove(friend.userId)
                        }
                    )
                }
            }

            2 -> {
                Text(
                    text = "요청 취소",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.eco_pretendard_bold)),
                        fontSize = 16.sp,
                        color = Color(ContextCompat.getColor(context, R.color.orange))
                    ),
                    modifier = Modifier
                        .width(60.dp)
                        .clickable {
                            friendViewModel.patchFriendRequestRemove(friend.userId)
                        }
                )
            }
        }
    }
    Divider(color = Color(ContextCompat.getColor(context, R.color.border)))
}

@Composable
fun FriendSearchDialog(
    allFriendList: List<FriendSearchList>,
    friendViewModel: FriendViewModel,
    onDismiss: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        text = {
            Column(modifier = Modifier.height(400.dp)) {

                CustomOutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = "친구 아이디",
                    trailingIcon = {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.icon_x),
                                contentDescription = "Clear text",
                                tint = Color(
                                    ContextCompat.getColor(
                                        LocalContext.current,
                                        R.color.assistive
                                    )
                                )
                            )
                        }
                    },
                    padding = 0
                )

                Spacer(modifier = Modifier.height(10.dp))

                // 친구 리스트 표시
                LazyColumn(modifier = Modifier.fillMaxHeight()) {
                    val filteredList = if (searchQuery.isNotEmpty()) {
                        allFriendList.filter {
                            it.nickname.contains(searchQuery, ignoreCase = true)
                        }
                    } else {
                        allFriendList
                    }

                    items(filteredList) { friend ->
                        FriendListItem(friend, friendViewModel)
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {}
    )
}

@Composable
fun FriendListItem(friend: FriendSearchList, friendViewModel: FriendViewModel) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f)
        ) {
            val painter = if (friend.thumbnail != null) {
                rememberAsyncImagePainter(friend.thumbnail)
            } else {
                painterResource(id = R.drawable.profile)
            }
            Image(
                painter = painter,
                contentDescription = null,
                Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = friend.nickname ?: "Unknown",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.eco_pretendard_normal)),
                        fontSize = 14.sp
                    )
                )
                Text(
                    text = friend.email ?: "Unknown",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.eco_pretendard_neutral)),
                        fontSize = 14.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        when (friend.friendType) {
            "DEFAULT" -> {
                Text(
                    text = "친구 요청",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.eco_pretendard_bold)),
                        fontSize = 16.sp,
                        color = Color(ContextCompat.getColor(context, R.color.primary))
                    ),
                    modifier = Modifier
                        .width(60.dp)
                        .clickable {
                        friendViewModel.postFriendRequest(friend.userId)
                        },
                )
            }

            "FRIEND" -> {
                Text(
                    text = "친구 삭제",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.eco_pretendard_bold)),
                        fontSize = 16.sp,
                        color = Color(ContextCompat.getColor(context, R.color.negative))
                    ),
                    modifier = Modifier
                        .width(60.dp)
                        .clickable {
                            friendViewModel.deleteFriend(friend.userId)
                        }
                )
            }

            "RECEIVED" -> {
                Row(
                    modifier = Modifier.width(65.dp)
                ) {
                    Text(
                        text = "수락",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.eco_pretendard_bold)),
                            fontSize = 16.sp,
                            color = Color(ContextCompat.getColor(context, R.color.positive))
                        ),
                        modifier = Modifier.clickable {
                            friendViewModel.postFriendRequestedApprove(friend.userId)
                        }
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "거절",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.eco_pretendard_bold)),
                            fontSize = 16.sp,
                            color = Color(ContextCompat.getColor(context, R.color.negative))
                        ),
                        modifier = Modifier.clickable {
                            friendViewModel.patchFriendRequestedRemove(friend.userId)
                        }
                    )
                }
            }

            "REQUESTED" -> {
                Text(
                    text = "요청 취소",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.eco_pretendard_bold)),
                        fontSize = 16.sp,
                        color = Color(ContextCompat.getColor(context, R.color.orange))
                    ),
                    modifier = Modifier
                        .width(60.dp)
                        .clickable {
                            friendViewModel.patchFriendRequestRemove(friend.userId)
                        }
                )
            }
        }
    }
    Divider(color = Color(ContextCompat.getColor(context, R.color.border)))
}