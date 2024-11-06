package com.example.graduationproject_aos.webrtc.models

data class RtcOffer(
    val type: String,
    val room: String?=null,
    val offer: Any?= null
)
