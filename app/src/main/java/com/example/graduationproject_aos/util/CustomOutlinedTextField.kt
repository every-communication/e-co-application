package com.example.graduationproject_aos.util

import android.content.Context
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.core.content.ContextCompat
import com.example.graduationproject_aos.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isPassword: Boolean = false,
    modifier: Modifier = Modifier,
    trailingIcon: (@Composable (() -> Unit))? = null,
    padding: Int = 24,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .height(52.dp)
            .fillMaxWidth()
            .padding(horizontal = padding.dp)
            .background(Color(ContextCompat.getColor(LocalContext.current, R.color.transparent))),
        textStyle = TextStyle(fontSize = 14.sp),
        placeholder = {
            Text(
                text = placeholder,
                color = Color(ContextCompat.getColor(LocalContext.current, R.color.assistive)),
                fontSize = 14.sp
            )
        },
        singleLine = true,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = if (isPassword) KeyboardOptions(keyboardType = KeyboardType.Password) else KeyboardOptions.Default,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = Color.White,
            cursorColor = Color.Black,
            focusedBorderColor = Color(ContextCompat.getColor(LocalContext.current, R.color.assistive)),
            unfocusedBorderColor = Color(ContextCompat.getColor(LocalContext.current, R.color.assistive))
        ),
        trailingIcon = trailingIcon
    )
}
