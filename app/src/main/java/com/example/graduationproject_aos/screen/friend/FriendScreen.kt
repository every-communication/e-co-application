package com.example.graduationproject_aos.screen.friend

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.graduationproject_aos.R
import com.example.graduationproject_aos.util.CustomStatusBar
import com.example.graduationproject_aos.util.showToast
import kotlinx.coroutines.launch

@Composable
fun FriendSceen(
    navController: NavHostController,
    bottomBarVisible: (Boolean) -> Unit,
) {
    val tabs = listOf("내 친구 목록", "친구 요청", "친구 요청중")
    val pagerState = rememberPagerState {
        tabs.size
    }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CustomStatusBar()
        Box(modifier = Modifier.fillMaxSize().weight(1f)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 18.dp, end = 18.dp, top = 18.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TabRow(
                    selectedTabIndex = pagerState.currentPage,
                    indicator = {},
                    divider = { Divider(color = Color(ContextCompat.getColor(context, R.color.border))) },
                    containerColor = Color.White,
                    contentColor = Color.Black
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            text = { Text(
                                title,
                                color = if (pagerState.currentPage == index) Color(ContextCompat.getColor(context, R.color.primary)) else Color.Black
                            ) },
                            selected = pagerState.currentPage == index,
                            modifier = Modifier
                                .width(200.dp)
                                .height(50.dp),
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)
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
                            .padding(vertical = 18.dp)
                    ) { page ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            when (page) {
                                0 -> FriendListScreen(page)
                                1 -> FriendListScreen(page)
                                2 -> FriendListScreen(page)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FriendListScreen(page: Int) {
    val context = LocalContext.current
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
        LazyColumn {
            items(100) { index ->
                ListItem(index)
            }
        }
    }
}

@Composable
fun ListItem(Index: Int) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = null,
                Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Column {
                Text(
                    text = "김건국",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.eco_pretendard_normal)),
                        fontSize = 14.sp
                    )
                )
                Text(
                    text = "konkuk@konkuk.ac.kr",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.eco_pretendard_neutral)),
                        fontSize = 14.sp
                    )
                )
            }
        }
        Text(
            text = "친구 요청",
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.eco_pretendard_normal)),
                fontSize = 14.sp
            ),
            modifier = Modifier.clickable {
                context.showToast("clickEvent!!")
            }
        )
    }
    Divider(color = Color(ContextCompat.getColor(context, R.color.border)))
}