package com.example.graduationproject_aos.webrtc

import android.util.Log
import com.example.graduationproject_aos.webrtc.models.MessageModel
import com.example.graduationproject_aos.webrtc.util.NewMessageInterface
import com.google.gson.Gson
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import kotlin.Exception

class SocketRepository(private val messageInterface: NewMessageInterface) {
    private var webSocket: WebSocketClient? = null
    private var userName: String? = null
    private val TAG = "SocketRepository"
    private val gson = Gson()

    fun initSocket(username: String, ipAddress: String) {
        userName = username
        //if you are using android emulator your local websocket address is going to be "ws://10.0.2.2:3000"
        //if you are using your phone as emulator your local address is going to be "ws://192.168.0.13:3000"
        //but if your websocket is deployed you add your websocket address here

//        webSocket = object : WebSocketClient(URI("ws://10.0.2.2:3000")){
        webSocket = object : WebSocketClient(URI("ws://$ipAddress:3000")){
            //        webSocket = object : WebSocketClient(URI("ws://172.20.10.2:3000")){
            override fun onOpen(handshakedata: ServerHandshake?) {
                sendMessageToSocket(
                    MessageModel(
                        "store_user", username, null, null
                    )
                )
            }

            override fun onMessage(message: String?) {
                try {
                    messageInterface.onNewMessage(gson.fromJson(message, MessageModel::class.java))
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }

            override fun onClose(code: Int, reason: String?, remote: Boolean) {
                Log.d(TAG, "onClose: $reason")
            }

            override fun onError(ex: Exception?) {
                Log.d(TAG, "onError: $ex")
            }
        }
        webSocket?.connect()
    }

    fun sendMessageToSocket(message: MessageModel) {
        try {
            webSocket?.send(Gson().toJson(message))
        }catch (e: Exception) {
            e.printStackTrace()
        }
    }
}