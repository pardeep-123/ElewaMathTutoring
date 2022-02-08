package com.elewamathtutoring.Activity.Chat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.elewamathtutoring.Activity.Chat.chatModel.chatThreads.GetInboxMessageListResponse
import com.elewamathtutoring.Activity.Chat.socket.SocketManagernewew
import com.elewamathtutoring.Activity.Chat.socket.SocketManagernewew.Companion.CHAT_LISTING_LISTNER
import com.elewamathtutoring.Activity.NotificationsActivity
import com.elewamathtutoring.Activity.SettingActivity
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.App
import com.elewamathtutoring.Util.constant.Constants
import com.pawskeeper.Adapter.MessageAdapter
import kotlinx.android.synthetic.main.fragment_messages_tab.*
import kotlinx.android.synthetic.main.fragment_messages_tab.view.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception
import java.util.*

class MessagesTabFragment : Fragment() ,View.OnClickListener,  SocketManagernewew.SocketInterface{
    private var socketManager: SocketManagernewew? = null
    lateinit var v: View
    lateinit var contex: Context
    lateinit var progresschat: ProgressBar
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_messages_tab, container, false)
        contex=requireContext()
        socketManager = App.getinstance().getSocketManagernn()
        socketManager!!.init()
        progresschat=v.findViewById(R.id.progresschat)
        onClicks()
        return v
    }

    private fun onClicks() {
      //  v.rootView.ivNotification.setOnClickListener(this)
        v.rootView.ivSetting.setOnClickListener(this)
        v.rootView.btnCreateGroup.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
           /* R.id.ivNotification -> {
                startActivity(Intent(context, NotificationsActivity::class.java))
            }*/
            R.id.ivSetting -> {
                startActivity(Intent(context, SettingActivity::class.java))
            }
            R.id.btnCreateGroup -> {
                startActivity(Intent(context, StudyGroupActivity::class.java))
            }
        }
    }

    override fun onResponse(event: String, vararg args: Any) {
        when (event) {
            CHAT_LISTING_LISTNER -> {
                try {
                    requireActivity().runOnUiThread(Runnable {
                        progresschat.visibility=View.GONE
                        val data = args.get(0) as JSONArray
                        Log.e("Socketdfdfd", "mma")
                        val list = ArrayList<GetInboxMessageListResponse>()
                        for (i in 0 until data.length())
                        {
                            Log.e("Socketdfdfd", "mma   " + data.length())
                            val objects = data.getJSONObject(i)
                            val getInboxMessageListResponse = GetInboxMessageListResponse()
                            getInboxMessageListResponse.id = objects.getInt("id")
                            getInboxMessageListResponse.userId = objects.getInt("userid")
                            getInboxMessageListResponse.user2Id = objects.getInt("user2Id")
                            getInboxMessageListResponse.createdAt = objects.getString("createdAt")
                            getInboxMessageListResponse.updatedAt = objects.getString("updatedAt")

                            getInboxMessageListResponse.lastMessage = objects.getString("lastMessage")
                            getInboxMessageListResponse.userName = objects.getString("userName")
                            getInboxMessageListResponse.userImage = objects.getString("userImage")
                            //                    getInboxMessageListResponse.unreadcount = objects.getInt("role")

                            list.add(getInboxMessageListResponse)
                        }
                        Log.e("Socketdfdfd", "list siz" + list.size)


                        requireActivity().runOnUiThread {
                            Log.e("Socketdfdfd", "list siz" + list.size)
                            val messageAdapter = MessageAdapter(contex, list)
                            messagenvendor_recycle.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                            messagenvendor_recycle.adapter = messageAdapter
                            //   Progress_chat.visibility=View.GONE
                        }

                        if (list.size != 0)
                        {
                            rel_nomessage.visibility = View.GONE
                            messagenvendor_recycle.visibility = View.VISIBLE
                            //  Progress_chat.visibility=View.GONE
                        } else {
                            rel_nomessage.visibility = View.VISIBLE
                            messagenvendor_recycle.visibility = View.GONE
                            //  Progress_chat.visibility=View.GONE
                        }
                    })

                }
                catch (e:Exception)
                {

                }
            }
        }
    }

    override fun onSocketCall(event: String?, vararg args: Any?) {
        Log.e("Socketdfdfdn", "onSocketCall")
    }

    override fun onSocketConnect(vararg args: Any?) {
        Log.e("Socketdfdfdn", "onSocketConnect")
    }

    override fun onSocketDisconnect(vararg args: Any?) {
        Log.e("Socketdfdfdn", "onSocketDisconnect")
    }

    override fun onError(event: String?, vararg args: Any?) {
        when(event){
            "errorSocket" -> {

            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        socketManager!!.onDisconnect()
    }
    private fun getChatData()
    {
        progresschat.visibility=View.VISIBLE
        val USER_IDValue: String = Constants.USER_IDValue
        val jsonObject = JSONObject()

        try
        {
            jsonObject.put("userId", USER_IDValue)
            Log.e("Socketdfdfd", "okk")
            socketManager!!.getInboxMessage(jsonObject)
        }
        catch (e: JSONException)
        {
            e.printStackTrace()
            Log.e("Socketdfdfd", "onGetChatListener" + e.toString())
        }
    }

    override fun onStart()
    {
        super.onStart()
        socketManager!!.onRegister(this)
    }
    override fun onResume() {
        super.onResume()
        getChatData()
    }
}