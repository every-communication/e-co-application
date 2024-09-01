package com.example.graduationproject_aos.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.graduationproject_aos.R
import com.example.graduationproject_aos.ui.theme.GraduationProject_AOSTheme

@Composable
fun HomeScreen() {
    GraduationProject_AOSTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.padding(160.dp))

                Spacer(modifier = Modifier.padding(20.dp))
                Button(
                    onClick = {
                    },
                    colors = ButtonDefaults.buttonColors(Color(0xFF1F855A))
                ) {
                    Text(text = "Game Start!")
                }
            }
        }
    }
}