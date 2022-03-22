package com.elewamathtutoring.Activity.Chat

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.rideandserve.utils.ImagePickerUtility
import com.elewamathtutoring.Activity.Chat.chatModel.ChatListModel
import com.elewamathtutoring.Activity.Chat.mathChat.AudioCallActivity
import com.elewamathtutoring.Activity.Chat.mathChat.VideoCallActivity
import com.elewamathtutoring.Activity.Chat.socket.SocketManager
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.App
import com.elewamathtutoring.Util.constant.Constants
import com.elewamathtutoring.Util.helper.extensions.getPrefrence
import com.elewamathtutoring.viewmodel.BaseViewModel
import com.google.gson.GsonBuilder
import com.pawskeeper.Adapter.ChatAdapter
import kotlinx.android.synthetic.main.activity_chat.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.FileInputStream
import java.io.IOException

class Chat_Activity :ImagePickerUtility(), View.OnClickListener, SocketManager.Observer {

    var receiverId = ""
    var dialog:Dialog?=null
    var Mediaimage=""
    var Block=""
    var type="1"
    var bundle:Bundle?=null
    var messageAdapter: ChatAdapter? = null
    var chatUserImage: String = ""
    var extension = ""
    var list = ArrayList<ChatListModel.ChatListItem>()
    private var socketManager: SocketManager? = null

    private var linearLayoutManager: LinearLayoutManager? = null

    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }
    override fun selectedImage(imagePath: String?, code: Int) {

        try {
            extension =
                imagePath?.substring(imagePath.lastIndexOf(".") + 1).toString() // Without dot jpg, png
        } catch (e: Exception) {
        }

        // send image as bit 64 in socket
        try {
            val jsonObject = JSONObject()
            jsonObject.put("userId", getPrefrence(Constants.USER_ID,""))
            jsonObject.put("user2Id", receiverId)
            jsonObject.put("message", getBase64FromPath(imagePath!!))
            jsonObject.put("messageType", "1")
            jsonObject.put("extension", extension)
            socketManager!!.sendMessage(jsonObject)

        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        socketManager = App.instance.getSocketManagernn()
        if (!socketManager!!.isConnected() || socketManager!!.getmSocket() == null) {
            socketManager!!.init()
        }
        ivBack.setOnClickListener(this)
        ivVideoCall.setOnClickListener(this)
        ivVoiceCall.setOnClickListener(this)
        ivAttachment.setOnClickListener(this)

        receiverId=intent.getStringExtra("receiverId").toString()


        setAdapter()
        MY_CHAT("t")


        init()
    }

    private fun setAdapter(){
        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        messageAdapter = ChatAdapter(this, list)
        rv_chat.layoutManager = linearLayoutManager
        rv_chat.adapter = messageAdapter


    }
  /*  override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        init()
    }*/

    fun init() {

        tv_name.text=intent.getStringExtra("chatUserName").toString()

        ivSendBtn.setOnClickListener {
            Log.e("ececewcrec", "lll" + Mediaimage)
            if (Et_chat_message!!.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "Please enter a message!!", Toast.LENGTH_LONG).show();
            } else {
                Block = ""
                try {
                    val jsonObject = JSONObject()
                    jsonObject.put("userId", getPrefrence(Constants.USER_ID,""))
                    jsonObject.put("user2Id", receiverId)
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
        Constants.Notification_chat=""
     //   socketManager!!.onDisconnect()
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
        Constants.Notification_chat=""
    }



    @SuppressLint("WrongConstant")
    fun viewLastMessage(){
        runOnUiThread {
            progresschat.visibility=View.GONE
            val li = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            li.setStackFromEnd(true)
            rv_chat.layoutManager = li
            rv_chat.adapter = messageAdapter

            if (list.isEmpty()) {
                rv_chat.visibility = View.GONE
            }
            else
            {
                rv_chat.visibility = View.VISIBLE
              }
        }
    }

    override fun onClick(p0: View?)
    {
        when (p0!!.id) {
            R.id.ivBack -> {
                onBackPressed()
            }
            R.id.ivVideoCall -> {
                startActivity(Intent(this,VideoCallActivity::class.java))
            }
            R.id.ivVoiceCall -> {
                startActivity(Intent(this,AudioCallActivity::class.java))
            }
            R.id.ivAttachment ->{
                getImage(this,0)
            }
        }
    }

    fun MY_CHAT(progress: String) // get all chat
    {
        if(progress.equals("f"))
        {
            progresschat.visibility=View.GONE
        }
        else
        {
            progresschat.visibility=View.VISIBLE
        }

        Log.e("Socketdfdfd",
            "Data  me" + getPrefrence(Constants.USER_ID, "") + "  reciver " + receiverId
        )
        val jsonObject = JSONObject()
        jsonObject.put("userId", getPrefrence(Constants.USER_ID,""))
        jsonObject.put("user2Id", receiverId)
        socketManager!!.getFriendChat(jsonObject)
    }

    // function for convert image to bit 64

    fun getBase64FromPath(path: String): String {
        var base64 = ""
        try {
            val file = File(path)
            val buffer = ByteArray(file.length().toInt() + 100)
            val length = FileInputStream(file).read(buffer)
            base64 = android.util.Base64.encodeToString(
                buffer, 0, length,
                android.util.Base64.DEFAULT
            )

        } catch (e: IOException) {
//e.printStackTrace()
        }
        return base64
    }

    override fun onResponseArray(event: String, args: JSONArray) {
        when (event) {
            SocketManager.GET_CHAT_EMITTER ->
                runOnUiThread {
                    progresschat.visibility=View.GONE
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
        when(event){

          /*  SocketManager.GET_CHAT_LISTNER_ONE_TO_ONE ->
                runOnUiThread {
                    progresschat.visibility=View.GONE
                    list.clear()
                    Log.e("Socketdfdfd", "222--inside-")
                    val data = args as JSONObject
                    var chat_list_bothside: Chat_list_bothside

                   *//* for (i in 0 until data.length()) {
                        val objects = data.getJSONObject(i)
                        chat_list_bothside = Chat_list_bothside()
                        chat_list_bothside.senderID = objects.getInt("SenderID")
                        chat_list_bothside.receiverId = objects.getInt("ReceiverId")
                        chat_list_bothside.createdAt = objects.getString("createdAt")
                        chat_list_bothside.message = objects.getString("message")
                        chat_list_bothside.msgType = objects.getInt("msgType")
                        chat_list_bothside.senderImage = objects.getString("SenderImage")
                        chat_list_bothside.receiverImage = objects.getString("ReceiverImage")
                        list.add(chat_list_bothside)
                    }*//*

                    messageAdapter = ChatAdapter(c, list)
                    viewLastMessage()
                }*/

            SocketManager.SEND_MESSAGE ->{
                val data= args as JSONObject
                Log.e("=====",data.toString())
                val gson = GsonBuilder().create()
                val userToCallList = gson.fromJson(data.toString(), ChatListModel.ChatListItem::class.java)
                list.add(userToCallList)

                messageAdapter?.notifyDataSetChanged()
                rv_chat.smoothScrollToPosition(list.size - 1)
            }
        }

    }

    override fun onError(event: String, vararg args: Array<*>) {
    }

    private fun scrollToBottom(){
        linearLayoutManager?.smoothScrollToPosition(rv_chat, null, messageAdapter?.itemCount!!)
    }
}
