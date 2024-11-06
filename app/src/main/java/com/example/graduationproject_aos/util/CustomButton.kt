package com.example.graduationproject_aos.util

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.graduationproject_aos.R

@Composable
fun CustomButton(
    text: String,
    backgroundColor: Color,
    textColor: Color,
    padding: Int,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    Text(
        text = text,
        style = TextStyle(
            fontFamily = FontFamily(Font(R.font.eco_pretendard_normal)),
            fontSize = 16.sp,
            color = textColor
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = padding.dp)
            .background(backgroundColor, RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(vertical = 12.dp, horizontal = 24.dp),
        textAlign = androidx.compose.ui.text.style.TextAlign.Center
    )
}