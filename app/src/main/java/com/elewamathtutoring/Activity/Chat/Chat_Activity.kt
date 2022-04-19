package com.elewamathtutoring.Activity.Chat

import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.rideandserve.utils.ImagePickerUtility
import com.bumptech.glide.Glide
import com.elewamathtutoring.Activity.Chat.chatModel.ChatListModel
import com.elewamathtutoring.Activity.Chat.call.AudioCallActivity
import com.elewamathtutoring.Activity.Chat.call.VideoCallActivity
import com.elewamathtutoring.Activity.Chat.socket.SocketManager
import com.elewamathtutoring.Models.Call.IncomingCallModel
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.App
import com.elewamathtutoring.Util.Validator
import com.elewamathtutoring.Util.constant.Constants
import com.elewamathtutoring.Util.helper.Helper
import com.elewamathtutoring.Util.helper.extensions.getPrefrence
import com.elewamathtutoring.api.Status
import com.elewamathtutoring.network.RestObservable
import com.elewamathtutoring.viewmodel.BaseViewModel
import com.google.gson.GsonBuilder
import com.pawskeeper.Adapter.ChatAdapter
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import kotlinx.android.synthetic.main.activity_add_bank_account.*
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_chat.ivBack
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class Chat_Activity : ImagePickerUtility(), View.OnClickListener, SocketManager.Observer,
    Observer<RestObservable> {
    private var mAlbumFiles: java.util.ArrayList<AlbumFile> = java.util.ArrayList()
    var receiverId = ""
    var groupId = ""
    var dialog: Dialog? = null
    var Mediaimage = ""
    var Block = ""
    var type = "1"
    var bundle: Bundle? = null
    var messageAdapter: ChatAdapter? = null
    var chatUserImage: String = ""
    var extension = ""
    var list = ArrayList<ChatListModel.ChatListItem>()
    var Imagelist = ArrayList<UploadImageResponse.Body>()
    private var socketManager: SocketManager? = null
    var firstimage = ""
    var oldImage = ""
    private var linearLayoutManager: LinearLayoutManager? = null
    lateinit var validator: Validator

    private var activityScope = CoroutineScope(Dispatchers.Main)
    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }
    override fun selectedImage(imagePath: String?, code: Int) {
        /////////// api  imagepath
        if (imagePath != null) {
            baseViewModel.imageUpload(this, imagePath, true)
        }
        baseViewModel.getCommonResponse().observe(this, this)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        socketManager = App.instance.getSocketManagernn()
        socketManager?.receiveMsgListener()
        if (!socketManager!!.isConnected() || socketManager!!.getmSocket() == null) {
            socketManager!!.init()
        }
        ivBack.setOnClickListener(this)
        ivVideoCall.setOnClickListener(this)
        ivVoiceCall.setOnClickListener(this)
        ivAttachment.setOnClickListener(this)

        receiverId = intent.getStringExtra("receiverId").toString()
        if (receiverId == "null") receiverId = "0"
        groupId = intent.getStringExtra("groupId").toString()
        setAdapter()
        MY_CHAT("t")
        init()
        ivVoiceCall.setOnClickListener {
                try {
                    val userId = getPrefrence(Constants.USER_ID, "")
                    val jsonObject = JSONObject()
                    jsonObject.put("userId", userId)
                    jsonObject.put("friendId", receiverId)
                    jsonObject.put("type", "1")
                    socketManager!!.callToUser(jsonObject)
                } catch (e: Exception) {
                }

            }

    }

    private fun setAdapter() {
        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        messageAdapter = ChatAdapter(this, list)
        rv_chat.layoutManager = linearLayoutManager
        rv_chat.adapter = messageAdapter
    }

    fun init() {
        tv_name.text = intent.getStringExtra("chatUserName").toString()
        ivSendBtn.setOnClickListener {
            Log.e("ececewcrec", "lll" + Mediaimage)
            if (Et_chat_message!!.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "Please enter a message!!", Toast.LENGTH_LONG).show();
            } else {
                Block = ""
                try {
                    val jsonObject = JSONObject()
                    jsonObject.put("userId", getPrefrence(Constants.USER_ID, ""))
                    if (receiverId != "0")
                        jsonObject.put("user2Id", receiverId)
                    else
                        jsonObject.put("groupId", groupId)
                    jsonObject.put("message", Et_chat_message!!.text.toString().trim())
                    jsonObject.put("messageType", "0")
                    socketManager!!.sendMessage(jsonObject)
                    Et_chat_message!!.setText("")
                    Et_chat_message!!.setSelection(0)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Constants.Notification_chat = ""
           socketManager!!.unRegister(this)
    }

    override fun onResume() {
        super.onResume()
        socketManager!!.unRegister(this)
        socketManager!!.onRegister(this)
        if (!socketManager!!.isConnected() || socketManager!!.getmSocket() == null) {
            socketManager!!.init()
        }

        socketManager!!.receivedMessageActivate()
    }

    override fun onStop() {
        super.onStop()
        Constants.Notification_chat = ""
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.ivBack -> {
                onBackPressed()
            }
            R.id.ivVideoCall -> {
                try {
                    val userId = getPrefrence(Constants.USER_ID, "")
                    val jsonObject = JSONObject()
                    jsonObject.put("userId", userId)
                    jsonObject.put("friendId", receiverId)
                    jsonObject.put("type", "2")
                    socketManager!!.callToUser(jsonObject)
                } catch (e: Exception) {
                }
            }
            R.id.ivAttachment -> {
                getImage(this, 0)
            }
        }
    }

    private fun selectImage() {
        Album.image(this).singleChoice().camera(true).columnCount(4).widget(
            Widget.newDarkBuilder(this).title(getString(R.string.app_name)).build()
        )
            .onResult { result ->
                mAlbumFiles.addAll(result)
                Glide.with(this).load(result[0].path).into(ivProfileSignUp)

                firstimage = result[0].path
            }.onCancel {
            }.start()
    }

    fun MY_CHAT(progress: String) {
        if (progress.equals("f")) {
            progresschat.visibility = View.GONE
        } else {
            progresschat.visibility = View.VISIBLE
        }
        Log.e(
            "Socketdfdfd",
            "Data  me" + getPrefrence(Constants.USER_ID, "") + "  reciver " + receiverId
        )
        val jsonObject = JSONObject()
        if (receiverId != "0") {
            jsonObject.put("userId", getPrefrence(Constants.USER_ID, ""))
            jsonObject.put("user2Id", receiverId)
            socketManager!!.getFriendChat(jsonObject)
        } else {
            jsonObject.put("userId", getPrefrence(Constants.USER_ID, ""))
            jsonObject.put("groupId", groupId)
            socketManager!!.getFriendChat(jsonObject)

        }

    }

    override fun onResponseArray(event: String, args: JSONArray) {
        when (event) {
            SocketManager.GET_CHAT_EMITTER ->
                runOnUiThread {
                    progresschat.visibility = View.GONE
                    val objects = args as JSONArray
                    val gson = GsonBuilder().create()
                    val chatListData = gson.fromJson(objects.toString(), ChatListModel::class.java)
                    list.addAll(chatListData)

                    val tsLong = System.currentTimeMillis() / 1000
                    val ts = tsLong.toString()
                    //  chat_list_bothside.createdAt = ts
//                    messageAdapter = ChatAdapter(c, list)
//                    rv_chat.adapter = messageAdapter
//                    val li = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//                    li.setStackFromEnd(true)
//                    rv_chat.layoutManager = li
                    messageAdapter!!.notifyDataSetChanged()
                    scrollToBottom()
                    Log.e("Socketdfdfd", "GET MASSAGE INSIDE---insider")
                }

        }
    }

    override fun onResponse(event: String, args: JSONObject) {
        when (event) {

            SocketManager.SEND_MESSAGE -> {
                activityScope.launch {
                    val data = args
                    Log.e("=====", data.toString())
                    val gson = GsonBuilder().create()
                    val userToCallList =
                        gson.fromJson(data.toString(), ChatListModel.ChatListItem::class.java)
                    list.add(userToCallList)

                    messageAdapter?.notifyDataSetChanged()
                    rv_chat.smoothScrollToPosition(list.size - 1)
                }
            } SocketManager.CALL_TO_USER ->{
                activityScope.launch {
                    val data = args
                    val gson = GsonBuilder().create()
                    val callResponse = gson.fromJson(data.toString(),IncomingCallModel::class.java)

                    val callscreen = Intent(this@Chat_Activity, VideoCallActivity::class.java)

                    callscreen.putExtra("friendName", callResponse.friendName)
                    callscreen.putExtra("friendId", callResponse.friendId.toString())
                    callscreen.putExtra("friendImage", callResponse.friendImage)
                    callscreen.putExtra("agoraToken", callResponse.token)
                    callscreen.putExtra("channelName", callResponse.channelName)
                    startActivity(callscreen)
                    callscreen.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    //
                }
            }
        }

    }

    override fun onError(event: String, vararg args: Array<*>) {
    }

    private fun scrollToBottom() {
        linearLayoutManager?.smoothScrollToPosition(rv_chat, null, messageAdapter?.itemCount!!)
    }

    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is UploadImageResponse) {
                    // send image as bit 64 in socket
                    try {
                        val jsonObject = JSONObject()
                        jsonObject.put("userId", getPrefrence(Constants.USER_ID, ""))
                        if (receiverId != "0")
                            jsonObject.put("user2Id", receiverId)
                        else
                            jsonObject.put("groupId", groupId)
                        //response.body.img
                        jsonObject.put("message", t.data.body.image)
                        jsonObject.put("messageType", "1")
                        jsonObject.put("extension", extension)
                        socketManager!!.sendMessage(jsonObject)

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            }
            Status.ERROR -> {
                if (t.error is UploadImageResponse)
                    Helper.showSuccessToast(this, t.error.message)
            }
        }
    }
}
