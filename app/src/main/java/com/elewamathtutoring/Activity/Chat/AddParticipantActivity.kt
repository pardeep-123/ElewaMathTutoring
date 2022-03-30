package com.elewamathtutoring.Activity.Chat

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.elewamathtutoring.Activity.Chat.socket.SocketManager
import com.elewamathtutoring.Activity.TeacherOrTutor.MainTeacherActivity
import com.elewamathtutoring.Adapter.ParentOrStudent.AddParticipantsAdapter
import com.elewamathtutoring.MainActivity
import com.elewamathtutoring.Model.GroupListModel

import com.elewamathtutoring.R
import com.elewamathtutoring.Util.App
import com.elewamathtutoring.Util.constant.Constants
import com.elewamathtutoring.Util.helper.extensions.getPrefrence
import com.elewamathtutoring.showToast
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_add_participant.*
import kotlinx.android.synthetic.main.activity_add_participant.ivBack
import org.json.JSONArray
import org.json.JSONObject
import java.util.ArrayList

class AddParticipantActivity : AppCompatActivity(), View.OnClickListener,SocketManager.Observer
            ,AddParticipantsAdapter.GroupId{
    lateinit var addParticipantsAdapter: AddParticipantsAdapter
    private var popupWindow: PopupWindow? = null
    var viewGroup: ViewGroup? = null
    private var socketManager : SocketManager?=null
    var groupName = ""
    var extension = ""
    var image = ""
    var groupId = ""
    var idArraylist = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_participant)
        ivBack.setOnClickListener(this)
        btnAdd.setOnClickListener(this)
        ivFilter.setOnClickListener(this)

        socketManager = App.instance.getSocketManagernn()

        // get data from intent
        groupName = intent.getStringExtra("groupName")!!
        image = intent.getStringExtra("image")!!
        extension = intent.getStringExtra("extension")!!

    }

    private fun setPopUpWindow() {
        val viewGroup: ViewGroup = window.decorView.rootView as ViewGroup
        val inflater =
            applicationContext?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.popup_add_participants, null)
        popupWindow = PopupWindow(view, 450, RelativeLayout.LayoutParams.WRAP_CONTENT, true)
        ivFilter.setOnClickListener {
            popupWindow?.showAsDropDown(it, 150, 48)
            applyDim(viewGroup, 0.5f)
        }

        popupWindow?.setOnDismissListener {
            clearDim(viewGroup)
        }
    }

    fun applyDim(parent: ViewGroup, dimAmount: Float) {
        val dim: Drawable = ColorDrawable(Color.BLACK)
        dim.setBounds(0, 0, parent.width, parent.height)
        dim.alpha = (255 * dimAmount).toInt()
        val overlay = parent.overlay
        overlay.add(dim)
    }

    fun clearDim(parent: ViewGroup) {
        val overlay = parent.overlay
        overlay.clear()
    }


    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.ivBack -> {
                finish()
            }
            R.id.btnAdd -> {
                //Imagename, name, memberIds, userId
                val jsonObject = JSONObject()
                jsonObject.put("userId", getPrefrence(Constants.USER_ID,""))
                val list = JSONArray(idArraylist)
                 jsonObject.put("memberIds", list)
                 jsonObject.put("imageName", image)
                 jsonObject.put("extension", extension)
                 jsonObject.put("name", groupName)
                socketManager!!.createGroup(jsonObject)

            }
            R.id.ivFilter -> {
                setPopUpWindow()
            }
        }
    }

    fun groupList(progress: String) // get all chat
    {
//        if(progress.equals("f"))
//        {
//            progresschat.visibility=View.GONE
//        }
//        else
//        {
//            progresschat.visibility=View.VISIBLE
//        }

        val jsonObject = JSONObject()
        jsonObject.put("userId", getPrefrence(Constants.USER_ID,""))
       // jsonObject.put("user2Id", receiverId)
        socketManager!!.getGroupList(jsonObject)
    }

    override fun onResponseArray(event: String, args: JSONArray) {
        when (event) {
            SocketManager.participantsList -> {
                try {
                    runOnUiThread {

                        val data = args
                        Log.e("Socketdfdfd", "mma")
                        val list = ArrayList<GroupListModel.GroupListModelItem>()
                        val gson = GsonBuilder().create()

                        val userToCallList =
                            gson.fromJson(data.toString(), GroupListModel::class.java)
                        list.addAll(userToCallList)

                        runOnUiThread {
                            addParticipantsAdapter = AddParticipantsAdapter(this,list,this)
                            rv_add_participants.adapter = addParticipantsAdapter
                            rv_add_participants.layoutManager = LinearLayoutManager(
                                this,
                                LinearLayoutManager.VERTICAL,
                                false
                            )

                        }
                    }

                }
                catch (e:Exception) {}
            }


            SocketManager.createGroupEmitter -> {
               groupId = (args[0] as JSONObject).getString("groupId")
                try {
                    val jsonObject = JSONObject()
                    jsonObject.put("groupId", groupId)
                    socketManager!!.joinRoom(jsonObject)

                } catch (e: Exception) {}
            }
        }
    }

    override fun onResponse(event: String, args: JSONObject) {

        when(event){
            // join room
            SocketManager.joinRoomEmitter ->{
                    runOnUiThread {
                        if (getPrefrence("userType", "").equals("1")) {
                            startActivity(Intent(this, MainActivity::class.java))
                            finishAffinity()
                        } else {
                            startActivity(Intent(this, MainTeacherActivity::class.java))
                            finishAffinity()
                        }

                }
            }
        }
    }

    override fun onError(event: String, vararg args: Array<*>) {

    }

    override fun onResume() {
        super.onResume()
        socketManager?.unRegister(this)
        socketManager?.onRegister(this)
        if (!socketManager!!.isConnected() || socketManager!!.getmSocket() == null) {
            socketManager!!.init()
        }

        groupList("f")
    }

    override fun groupIds(id: Int) {
        if (idArraylist.contains(id))
            idArraylist.remove(id)
        else
            idArraylist.add(id)

    }
}