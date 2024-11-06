package com.example.graduationproject_aos.webrtc.models

data class RtcCandidate(
    val type: String,
    val room: String?=null,
    val candidate: Any?= null
)
