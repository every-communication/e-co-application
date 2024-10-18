package com.example.graduationproject_aos.webrtc.util

import com.example.graduationproject_aos.webrtc.models.MessageModel

interface NewMessageInterface {
    fun onNewMessage(message: MessageModel)
}