package com.example.graduationproject_aos.webrtc.models

data class RtcAnswer (
    val type: String,
    val room: String?=null,
    val answer: Any?=null
    )