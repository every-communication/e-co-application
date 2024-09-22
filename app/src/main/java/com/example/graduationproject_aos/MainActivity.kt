package com.example.graduationproject_aos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.graduationproject_aos.screen.login.LoginScreen
import com.example.graduationproject_aos.screen.login.LoginViewModel
import com.example.graduationproject_aos.ui.theme.GraduationProject_AOSTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GraduationProject_AOSTheme {
                val navController = rememberNavController()
//                var bottomBarVisible by remember { mutableStateOf(false) }
                val loginViewModel: LoginViewModel = viewModels<LoginViewModel>().value

                Scaffold(
//                    bottomBar = {
//                        if (bottomBarVisible) {
//                            BottomBar(navController = navController, bottomBarVisible = true)
//                        }
//                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        NaviGraph(
                            loginViewModel,
                            navController
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GraduationProject_AOSTheme {
        Greeting("Android")
    }
}