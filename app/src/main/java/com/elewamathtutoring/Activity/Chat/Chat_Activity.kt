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
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.elewamathtutoring.Activity.Chat.chatModel.Chat_list_bothside
import com.elewamathtutoring.Activity.Chat.mathChat.AudioCallActivity
import com.elewamathtutoring.Activity.Chat.mathChat.VideoCallActivity
import com.elewamathtutoring.Activity.Chat.socket.SocketManagernewew
import com.elewamathtutoring.Activity.Chat.socket.SocketManagernewew.Companion.GET_CHAT_LISTNER_ONE_TO_ONE
import com.elewamathtutoring.Activity.Chat.socket.SocketManagernewew.Companion.SEND_MESSAGE_LISTNER
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.App
import com.elewamathtutoring.Util.constant.Constants
import com.elewamathtutoring.Util.helper.extensions.getPrefrence
import com.elewamathtutoring.viewmodel.BaseViewModel
import com.pawskeeper.Adapter.ChatAdapter
import kotlinx.android.synthetic.main.activity_chat.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import kotlin.collections.ArrayList

class Chat_Activity :AppCompatActivity(), View.OnClickListener, SocketManagernewew.SocketInterface {

    var receiverId = ""
    lateinit var c: Chat_Activity
    var dialog:Dialog?=null
    var Mediaimage=""
    var Block=""
    var type="1"
    var bundle:Bundle?=null
    var messageAdapter: ChatAdapter? = null
    var chatUserImage: String = ""
    var list = ArrayList<Chat_list_bothside>()
    private var socketManager: SocketManagernewew? = null
    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        socketManager = App.getinstance().getSocketManagernn()
        socketManager!!.init()
        ivBack.setOnClickListener(this)
        ivVideoCall.setOnClickListener(this)
        ivVoiceCall.setOnClickListener(this)

        receiverId=intent.getStringExtra("receiverId").toString()
        MY_CHAT("t")
        init()
    }

  /*  override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        init()
    }*/

    fun init() {
        c = this
        tv_name.text=intent.getStringExtra("chatUserName").toString()

        ivSendBtn.setOnClickListener(View.OnClickListener
        {
            Log.e("ececewcrec", "lll" + Mediaimage)
            if (Et_chat_message!!.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "Please enter a message!!", Toast.LENGTH_LONG).show();
            } else {
                Block = ""
                try {
                    val jsonObject = JSONObject()
                    jsonObject.put("userId", Constants.USER_IDValue)
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
        })
}

    override fun onDestroy() {
        super.onDestroy()
        Constants.Notification_chat=""
        socketManager!!.onDisconnect()
    }

    override fun onStart()
    {
        super.onStart()
        socketManager!!.unRegister(this)
        socketManager!!.onRegister(this)
    }

    override fun onStop() {
        super.onStop()
        Constants.Notification_chat=""
    }

    override fun onSocketCall(event: String?, vararg args: Any?) {
        Log.e("Socketdfdfdn", "onSocketCall")
    }

    override fun onSocketConnect(vararg args: Any?) {
        Log.e("Socketdfdfdn", "onSocketConnect123")
    }

    override fun onSocketDisconnect(vararg args: Any?) {
        Log.e("Socketdfdfdn", "onSocketDisconnect"+receiverId)
       // MY_CHAT("f")
    }

    override fun onError(event: String?, vararg args: Any?) {
        when(event){
            "errorSocket" -> {
                Log.e("Socketdfdfdn", "errorSocket")
               // MY_CHAT("f")
            }
        }
    }

    override fun onResponse(event: String, vararg args: Any) {
        when (event) {
            SEND_MESSAGE_LISTNER ->
                runOnUiThread(Runnable
                {
                    val objects = args[0] as JSONObject
                    val chat_list_bothside: Chat_list_bothside
                    chat_list_bothside = Chat_list_bothside()
                    chat_list_bothside.senderID = objects.getInt("SenderID")
                    chat_list_bothside.receiverId = objects.getInt("ReceiverId")
                    chat_list_bothside.createdAt = objects.getString("createdAt")
                    chat_list_bothside.message = objects.getString("message")
                    chat_list_bothside.senderImage = objects.getString("SenderImage")
                    chat_list_bothside.receiverImage = objects.getString("ReceiverImage")
                    list.add(chat_list_bothside)
                    val tsLong = System.currentTimeMillis() / 1000
                    val ts = tsLong.toString()
                    chat_list_bothside.createdAt = ts
               //    messageAdapter = ChatAdapter(c, list)
//                    rv_chat.adapter = messageAdapter
                    val li = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                    li.setStackFromEnd(true)
                    rv_chat.layoutManager = li
                    messageAdapter!!.notifyDataSetChanged()
                    Log.e("Socketdfdfd", "GET MASSAGE INSIDE---insider")
                })

            GET_CHAT_LISTNER_ONE_TO_ONE ->
                runOnUiThread(Runnable
                { list.clear()
                    Log.e("Socketdfdfd", "222--inside-")
                    val data = args[0] as JSONArray
                    var chat_list_bothside: Chat_list_bothside

                    for (i in 0 until data.length()) {
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
                    }

                    messageAdapter = ChatAdapter(c, list)
                    viewLastMessage()
                })
        }
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
                startActivity(Intent(c,VideoCallActivity::class.java))
            }
            R.id.ivVoiceCall -> {
                startActivity(Intent(c,AudioCallActivity::class.java))
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
        jsonObject.put("userId", Constants.USER_IDValue)
        jsonObject.put("user2Id", receiverId)
        socketManager!!.Get_chat(jsonObject)
    }
}
