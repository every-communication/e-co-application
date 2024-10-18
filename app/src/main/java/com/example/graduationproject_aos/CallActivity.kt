package com.example.graduationproject_aos

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.graduationproject_aos.databinding.ActivityCallBinding
import com.example.graduationproject_aos.webrtc.RTCClient
import com.example.graduationproject_aos.webrtc.SocketRepository
import com.example.graduationproject_aos.webrtc.models.IceCandidateModel
import com.example.graduationproject_aos.webrtc.models.MessageModel
import com.example.graduationproject_aos.webrtc.util.NewMessageInterface
import com.example.graduationproject_aos.webrtc.util.PeerConnectionObserver
import com.example.graduationproject_aos.webrtc.util.RTCAudioManager
import com.google.gson.Gson
import org.webrtc.IceCandidate
import org.webrtc.MediaStream
import org.webrtc.SessionDescription

class CallActivity : AppCompatActivity(), NewMessageInterface {

    lateinit var binding: ActivityCallBinding
    private var userName: String? = null
    private var ipAddress: String? = null
    private var socketRepository: SocketRepository? = null
    private var rtcClient: RTCClient? = null
    private val TAG = "CallActivity"
    private var target: String = ""
    private val gson = Gson()
    private var isMute = false
    private var isCameraPause = false
    private val rtcAudioManager by lazy { RTCAudioManager.create(this) }
    private var isSpeakerMode = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCallBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userName = intent.getStringExtra("userId") ?: "null"
        ipAddress = intent.getStringExtra("ipAddress") ?: "null"
        init()
    }

    private fun init() {
        socketRepository = SocketRepository(this)
        if (userName != null && ipAddress != null) {
            socketRepository!!.initSocket(userName!!, ipAddress!!)
        }
//        userName?.let { socketRepository?.initSocket(it) }
        rtcClient = RTCClient(
            application,
            userName!!,
            socketRepository!!,
            object : PeerConnectionObserver() {

                override fun onIceCandidate(p0: IceCandidate?) {
                    //sending to IceCandidate
                    super.onIceCandidate(p0)
                    rtcClient?.addIceCandidate(p0)
                    val candidate = hashMapOf(
                        "sdpMid" to p0?.sdpMid,
                        "sdpMLineIndex" to p0?.sdpMLineIndex,
                        "sdpCandidate" to p0?.sdp
                    )

                    socketRepository?.sendMessageToSocket(
                        MessageModel("ice_candidate", userName, target, candidate)
                    )

                }

                override fun onAddStream(p0: MediaStream?) {
                    //stream is added to your pure connection -> remoteSurface에 보여줌
                    super.onAddStream(p0)
                    p0?.videoTracks?.get(0)?.addSink(binding.remoteView)
                    Log.d(TAG, "onAddStream: $p0")
                }
            })
        rtcAudioManager.setDefaultAudioDevice(RTCAudioManager.AudioDevice.SPEAKER_PHONE)

        binding.apply {
            callBtn.setOnClickListener {
                socketRepository?.sendMessageToSocket(
                    MessageModel(
                        "start_call", userName, targetUserNameEt.text.toString(), null
                    )
                )
                target = targetUserNameEt.text.toString()
            }

            switchCameraButton.setOnClickListener {
                rtcClient?.switchCamera()
            }

            micButton.setOnClickListener {
                if (isMute) {
                    isMute = false
                    micButton.setImageResource(R.drawable.ic_baseline_mic_off_24)
                } else {
                    isMute = true
                    micButton.setImageResource(R.drawable.ic_baseline_mic_24)
                }
                rtcClient?.toggleAudio(isMute)
            }

            videoButton.setOnClickListener {
                if (isCameraPause) {
                    isCameraPause = false
                    videoButton.setImageResource(R.drawable.ic_baseline_videocam_off_24)
                } else {
                    isCameraPause = true
                    videoButton.setImageResource(R.drawable.ic_baseline_videocam_24)
                }
                rtcClient?.toggleCamera(isCameraPause)
            }

            audioOutputButton.setOnClickListener {
                if (isSpeakerMode) {
                    isSpeakerMode = false
                    audioOutputButton.setImageResource(R.drawable.ic_baseline_hearing_24)
                    rtcAudioManager.setDefaultAudioDevice(RTCAudioManager.AudioDevice.EARPIECE)
                } else {
                    isSpeakerMode = true
                    audioOutputButton.setImageResource(R.drawable.ic_baseline_speaker_up_24)
                    rtcAudioManager.setDefaultAudioDevice(RTCAudioManager.AudioDevice.SPEAKER_PHONE)

                }

            }
            endCallButton.setOnClickListener {
                setCallLayoutGone()
                setWhoToCallLayoutVisible()
                setIncomingCallLayoutGone()
                rtcClient?.endCall()
            }
        }
    }

    override fun onNewMessage(message: MessageModel) {
        Log.d(TAG, "onNewMessage: $message")
        when (message.type) {
            "call_response" -> {
                if (message.data == "user is not online") {
                    //user is not reachable
                    runOnUiThread { //현재 socketThread에 있으므로
                        Toast.makeText(this, "user is not reachable", Toast.LENGTH_LONG).show()
                    }
                } else {
                    //we are ready for call, we started a call
                    runOnUiThread {
                        setWhoToCallLayoutGone()
                        setCallLayoutVisible()
                        binding.apply {
                            rtcClient?.initializeSurfaceView(localView)
                            rtcClient?.initializeSurfaceView(remoteView)
                            rtcClient?.startLocalVideo(localView)
                            rtcClient?.call(targetUserNameEt.text.toString())
                        }
                    }
                }
            }

            "answer_received" -> {
                val session = SessionDescription(
                    SessionDescription.Type.ANSWER,
                    message.data.toString()
                )
                rtcClient?.onRemoteSessionReceived(session)
                runOnUiThread {
                    binding.remoteViewLoading.visibility = View.GONE
//                    binding.remoteView.setScalingType(RendererCommon.ScalingType.SCALE_ASPECT_FILL)
                }
            }

            "offer_received" -> {
                runOnUiThread {
                    setIncomingCallLayoutVisible()
                    binding.incomingNameTV.text = "${message.name.toString()} is calling you"
                    binding.acceptButton.setOnClickListener {
                        setIncomingCallLayoutGone()
                        setWhoToCallLayoutGone()
                        setCallLayoutVisible()
//                        binding.remoteView.setScalingType(RendererCommon.ScalingType.SCALE_ASPECT_FILL)
                        binding.apply {
                            rtcClient?.initializeSurfaceView(localView)
                            rtcClient?.initializeSurfaceView(remoteView)
                            rtcClient?.startLocalVideo(localView)
                        }
                        val session = SessionDescription(
                            SessionDescription.Type.OFFER,
                            message.data.toString()
                        )
                        rtcClient?.onRemoteSessionReceived(session)
                        rtcClient?.answer(message.name!!) //message.name -> caller임
                        target = message.name!!
                        binding.remoteViewLoading.visibility = View.GONE
                    }
                    binding.rejectButton.setOnClickListener {
                        setIncomingCallLayoutGone()
                    }
                }
            }

            "ice_candidate" -> {
                try {
                    val receivingCandidate =
                        gson.fromJson(gson.toJson(message.data), IceCandidateModel::class.java)
                    val iceCandidate = IceCandidate(
                        receivingCandidate.sdpMid,
                        Math.toIntExact(receivingCandidate.sdpMLineIndex.toLong()),
                        receivingCandidate.sdpCandidate
                    )
                    rtcClient?.addIceCandidate(iceCandidate)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun setIncomingCallLayoutGone() {
        binding.incomingCallLayout.visibility = View.GONE
    }

    private fun setIncomingCallLayoutVisible() {
        binding.incomingCallLayout.visibility = View.VISIBLE
    }

    private fun setCallLayoutGone() {
        binding.callLayout.visibility = View.GONE
    }

    private fun setCallLayoutVisible() {
        binding.callLayout.visibility = View.VISIBLE
    }

    private fun setWhoToCallLayoutGone() {
        binding.whoToCallLayout.visibility = View.GONE
    }

    private fun setWhoToCallLayoutVisible() {
        binding.whoToCallLayout.visibility = View.VISIBLE
    }
}