package com.example.graduationproject_aos.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.graduationproject_aos.R

@Composable
fun CustomStatusBar() {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(painter = painterResource(id = R.drawable.ecologo), contentDescription = null)
        Image(painter = painterResource(id = R.drawable.alarm), contentDescription = null)
    }
    Divider(color = Color(ContextCompat.getColor(context, R.color.border)))
}