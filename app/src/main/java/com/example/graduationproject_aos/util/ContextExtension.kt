package com.example.graduationproject_aos.util

import android.content.Context
import android.widget.Toast

fun Context.showToast(
    message: String,
    duration : Int= Toast.LENGTH_SHORT
) {
    Toast.makeText(
        /* context = */ this,
        /* text = */ message,
        /* duration = */ duration
    ).show()
}