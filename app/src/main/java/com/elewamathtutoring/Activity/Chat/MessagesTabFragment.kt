package com.elewamathtutoring.Activity.Chat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.elewamathtutoring.Activity.Chat.chatModel.chatThreads.GetMessageInboxModel
import com.elewamathtutoring.Activity.Chat.socket.SocketManager
import com.elewamathtutoring.Activity.ParentOrStudent.settings.SettingActivity
import com.elewamathtutoring.Adapter.MessageAdapter
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.App
import com.elewamathtutoring.Util.constant.Constants
import com.elewamathtutoring.Util.helper.extensions.getPrefrence
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_messages_tab.*
import kotlinx.android.synthetic.main.fragment_messages_tab.view.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class MessagesTabFragment : Fragment() ,View.OnClickListener,  SocketManager.Observer{
    lateinit var socketManager: SocketManager
    lateinit var v: View
    lateinit var contex: Context
    lateinit var progresschat: ProgressBar
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_messages_tab, container, false)
        contex=requireContext()
        progresschat=v.findViewById(R.id.progresschat)
        onClicks()
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        socketManager = App.instance.getSocketManagernn()!!
        if (!socketManager.isConnected() || socketManager.getmSocket() == null) {
            socketManager.init()
        }
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

    private fun getChatData()
    {
        progresschat.visibility=View.VISIBLE
        val USER_IDValue = getPrefrence(Constants.USER_ID,"")
        val jsonObject = JSONObject()

        try
        {
            jsonObject.put("userId", USER_IDValue)
            Log.e("Socketdfdfd", "okk")
            socketManager.getChatList(jsonObject)
        }
        catch (e: JSONException)
        {
            e.printStackTrace()
            Log.e("Socketdfdfd", "onGetChatListener" + e.toString())
        }
    }

    override fun onResume() {
        super.onResume()
        socketManager.unRegister(this)
        socketManager.onRegister(this)
        if (!socketManager.isConnected() || socketManager.getmSocket() == null) {
            socketManager.init()
        }
        getChatData()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        socketManager?.unRegister(this)
    }
    override fun onResponseArray(event: String, args: JSONArray) {
        when (event) {
            SocketManager.CHAT_LISTING_EMITTER -> {
                try {
                    requireActivity().runOnUiThread {
                        progresschat.visibility = View.GONE
                        val data = args
                        Log.e("Socketdfdfd", "mma")
                        val list = ArrayList<GetMessageInboxModel.GetMessageInboxModelItem>()
                        val mObject = data as JSONArray
                        val gson = GsonBuilder().create()

                        val userToCallList =
                            gson.fromJson(mObject.toString(), GetMessageInboxModel::class.java)
                        list.addAll(userToCallList)
                        Log.e("Socketdfdfd", "list siz" + list.size)


                        requireActivity().runOnUiThread {
                            Log.e("Socketdfdfd", "list siz" + list.size)
                            val messageAdapter = MessageAdapter(contex, list)
                            messagenvendor_recycle.layoutManager = LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.VERTICAL,
                                false)
                            messagenvendor_recycle.adapter = messageAdapter
                            //   Progress_chat.visibility=View.GONE
                        }

                        if (list.size != 0) {
                            rel_nomessage.visibility = View.GONE
                            messagenvendor_recycle.visibility = View.VISIBLE
                            //  Progress_chat.visibility=View.GONE
                        } else {
                            rel_nomessage.visibility = View.VISIBLE
                            messagenvendor_recycle.visibility = View.GONE
                            //  Progress_chat.visibility=View.GONE
                        }
                    }

                }
                catch (e:Exception){}
            }
        }
    }

    override fun onResponse(event: String, args: JSONObject) {

    }

    override fun onError(event: String, vararg args: Array<*>) {

    }
}