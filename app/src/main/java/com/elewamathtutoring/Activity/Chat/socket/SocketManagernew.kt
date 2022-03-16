package com.elewamathtutoring.Activity.Chat.socket


import android.os.Handler
import android.os.Looper
import android.util.Log
import com.elewamathtutoring.Util.constant.Constants
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONObject
import java.net.URISyntaxException
import java.util.*

 class SocketManagernewew {


    private val TAG = SocketManagernewew::class.java.canonicalName

    // Enter ON Method Name here

    fun init() {
        try {
            val options = IO.Options()
          //  options.reconnection = true
           // options.reconnectionAttempts = Int.MAX_VALUE
            Log.e("Constants.BASE_URL", Constants.SOCKET_BASE_URL)
            mSocket = IO.socket(Constants.SOCKET_BASE_URL, options)
            if (observerList == null || observerList!!.size == 0) {
                observerList = ArrayList()
            }

            onConnect()
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }
    }


    companion object {
        private var mSocketClass: SocketManagernewew? = null
        val CONNECT_USER = "connect_user"
         val SEND_MESSAGE = "send_message"
         val CHAT_LISTING_EMITTER = "get_chat_list" //chat with all listing
         val GET_CHAT_EMITTER = "get_chat"
         val GET_READ_UNREAD_EMITTER = "read_message"
        //listener
         val CONNECT_LISTENER = "connect_listener"
         val SEND_MESSAGE_LISTNER = "new_message" // send message
         val CHAT_LISTING_LISTNER = "get_list" //chat with all listing outside
         val GET_CHAT_LISTNER_ONE_TO_ONE = "my_chat" // one to one chat
         val GET_READDATA_LISTNER = "read_data_status"


        private var observerList: ArrayList<SocketInterface>? = null


        fun getSocket(): SocketManagernewew? {
            if (mSocketClass == null) mSocketClass = SocketManagernewew()
            return mSocketClass
        }
    }

    var mSocket: Socket? = null
    var isconnected = false

    fun isConnected(): Boolean {
        return mSocket != null && mSocket!!.connected()
    }

    fun onRegister(observer: SocketInterface) {
      Log.e("Socketdfdfdn", "onRegister")
        if (observerList != null) {
            observerList!!.add(observer)
        } else {
            observerList = ArrayList()
            observerList!!.add(observer)
        }
    }

    fun unRegister(observer: SocketInterface) {
        if (observerList != null) {
            for (i in observerList!!.indices) {
                val model = observerList!![i]
                if (model === observer) {
                    observerList!!.remove(model)
                }
            }
        }
    }


    /**
     * Default Listener
     * Define what you want to do when connection is established
     */
    private val onConnect =
        Emitter.Listener { args ->
                  if (!mSocket!!.connected()) {
                    val user_id: Int = Constants.USER_IDValue.toInt()
                    if (user_id > 0) {
                      val jsonObject = JSONObject()
                      jsonObject.put("userId", 0)
                      sendDataToServer(CONNECT_USER, jsonObject)
                      Log.e("Socketdfdfd", "===!connected===Donee=")
                      for (observer in observerList!!) {
                        observer.onSocketConnect(*args)
                      }
                    }
                  } else {
                    Log.e("Socketdfdfd", "===!connected====")
                  }



        }

    /**
     * Default Listener
     * Define what you want to do when connection is disconnected
     */
    private val onDisconnect =
        Emitter.Listener { args ->
          Handler(Looper.getMainLooper()).post {
            for (observer in observerList!!)
            {
              observer.onSocketDisconnect(*args)
            }
          }
        }

    /**
     * Default Listener
     * Define what you want to do when there's a connection error
     */
    private val onConnectError =
        Emitter.Listener { args ->
            // Get a handler that can be used to post to the main thread
            Handler(Looper.getMainLooper()).post {
                for (observer in observerList!!) {
                    observer.onError("ERROR", *args)
                }
                Log.e(TAG, "Run" + args[0])
            }
        }

    /**
     * Call this method in onCreate and onResume
     */
    fun onConnect() {
        if (!mSocket!!.connected()) {
            mSocket!!.on(Socket.EVENT_CONNECT, onConnect)
            mSocket!!.on(Socket.EVENT_RECONNECT_FAILED, onDisconnect)
            mSocket!!.on(Socket.EVENT_CONNECT_ERROR, onConnectError)
            mSocket!!.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError)
            mSocket!!.connect()
        } else {
            Log.e("ConnectSocket", "connectSocketManager")
        }
    }

    /**
     * Call this method in onPause and onDestroy
     */
    fun onDisconnect() {
        Log.e("DisconnectSocket", "DisconnectSocket")
        isconnected = false
        mSocket!!.off(Socket.EVENT_CONNECT, onDisconnect)
        mSocket!!.off(Socket.EVENT_DISCONNECT, onDisconnect)
        mSocket!!.off(Socket.EVENT_CONNECT_ERROR, onDisconnect)
        mSocket!!.off(Socket.EVENT_CONNECT_TIMEOUT, onDisconnect)
        mSocket!!.disconnect()
    }

    /*
     * On Method call backs from server
     * */
    fun sendMessage(jsonObject: JSONObject?)
    {
        if (!mSocket!!.connected()) {
            mSocket!!.connect()

            mSocket!!.emit(SEND_MESSAGE, jsonObject)
            mSocket!!.off(SEND_MESSAGE_LISTNER)
            mSocket!!.on(SEND_MESSAGE_LISTNER, onBodyListener);

            Log.e("Socketdfdfd", "SEND_MESSAGE00")
        }
        else
        {
            mSocket!!.emit(SEND_MESSAGE, jsonObject)
            mSocket!!.off(SEND_MESSAGE_LISTNER)
            mSocket!!.on(SEND_MESSAGE_LISTNER, onBodyListener)
            Log.e("Socketdfdfd", "SEND_MESSAGE")
        }
    }

    fun getInboxMessage(jsonObject: JSONObject) {
        if (!mSocket!!.connected()) {
            mSocket!!.connect()
            mSocket!!.emit(CHAT_LISTING_EMITTER, jsonObject)
            mSocket!!.off(CHAT_LISTING_LISTNER)
            mSocket!!.on(CHAT_LISTING_LISTNER, onGetChatmessages)
        }
        else
        {
            mSocket!!.emit(CHAT_LISTING_EMITTER, jsonObject)
            mSocket!!.on(CHAT_LISTING_LISTNER, onGetChatmessages)
        }
    }
    private val onGetChatmessages = Emitter.Listener { args ->
        Log.e("Socketdfdfd", "CHAT_LISTING_LISTNER out side"+args[0].toString())

        for (observeri in observerList!!) {
            //Log.e("Socketdfdfd", "##### side"+args[0].toString())
            observeri.onResponse(CHAT_LISTING_LISTNER, args[0])
        }
    }


    fun Get_chat(jsonObject: JSONObject?) { // in sider chat
        Log.e("Socketdfdfd", "onGetChatListener000")
        if (!mSocket!!.connected()) {
            mSocket!!.connect()
            mSocket!!.emit(GET_CHAT_EMITTER, jsonObject)
            mSocket!!.off(GET_CHAT_LISTNER_ONE_TO_ONE)
            mSocket!!.on(GET_CHAT_LISTNER_ONE_TO_ONE, onGetChatListListener)
        }
        else
        {
            mSocket!!.emit(GET_CHAT_EMITTER, jsonObject)
            mSocket!!.on(GET_CHAT_LISTNER_ONE_TO_ONE, onGetChatListListener)
        }
    }


    private val onGetChatListListener = Emitter.Listener { args ->  //one to one chat
        Log.e("Socketdfdfd", "onGetChatListListener")
        for (observerlist in observerList!!) {
            observerlist.onResponse(GET_CHAT_LISTNER_ONE_TO_ONE, args[0])
        }
    }




    private val onConnectResponce = Emitter.Listener { args ->
        Log.e("Socketdfdfd", "===ConnectTEDww==")
        for (observerlist in observerList!!) {
            Log.e("Socketdfdfd", "===ConnectTED==")
            // no need for call
            // observerlist.onResponse(GET_READDATA_LISTNER, args)
        }
    }

    private val onBodyListener = Emitter.Listener { args ->
        Log.e("Socketdfdfdeee", "ONB")
        for (observerr in observerList!!) {
            Log.e("Socketdfdfdeee", "ONBODY" + "   " + args[0])
            observerr.onResponse(SEND_MESSAGE_LISTNER, args[0])
        }
    }
    /*
     * Send Data to server by use of socket
     * */
    fun sendDataToServer(
        methodName: String?,
        mObject: Any
    ) { // Get a handler that can be used to post to the main thread
        Handler(Looper.getMainLooper()).post {
            mSocket!!.emit(methodName, mObject)
            Log.e(methodName, mObject.toString())
        }
    }

    /*
     * Interface for Socket Callbacks
     * */
    interface SocketInterface {
        fun onResponse(event: String, vararg args: Any)
        fun onSocketCall(event: String?, vararg args: Any?)
        fun onSocketConnect(vararg args: Any?)
        fun onSocketDisconnect(vararg args: Any?)
        fun onError(event: String?, vararg args: Any?)
    }
}