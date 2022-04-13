package com.elewamathtutoring.Activity.Chat.socket

import android.util.Log
import com.elewamathtutoring.Util.constant.Constants
import com.elewamathtutoring.Util.helper.extensions.getPrefrence
import io.socket.client.Ack
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.net.URISyntaxException
import java.util.*

class SocketManager {
    // sockets listener
    private val errorMessage = "error_message"
    private val send_lat_long = "send_lat_long"

    companion object {
        //for chat
        //emitter
        val CONNECT_USER = "connect_user"
        val SEND_MESSAGE = "send_message"
        val CHAT_LISTING_EMITTER = "chat_listing" //chat with all listing
        val GET_CHAT_EMITTER = "get_message"
        val GET_READ_UNREAD_EMITTER = "read_message"
        val participantsList = "participants_list"
        val createGroupEmitter = "create_group"
        val joinRoomEmitter = "join_room"
        //listener
        val CONNECT_LISTENER = "connect_listener"
        val SEND_MESSAGE_LISTNER = "sendMessage" // send message
        val CHAT_LISTING_LISTNER = "chatListing" //chat with all listing outside
        val GET_CHAT_LISTNER_ONE_TO_ONE = "getMessage" // one to one chat
        val groupChatListner = "groupChatListner" // one to one chat
        val GET_READDATA_LISTNER = "readMessage"

        val CALL_TO_USER = "call_to_user"
        val END_CALL = "end_call"

    }


    private var mSocket: Socket? = null
    private var observerList: MutableList<Observer>? = null

    fun getmSocket(): Socket? {
        return mSocket
    }

    private fun getSocket(): Socket? {
        run {
            try {
                mSocket = IO.socket(Constants.SOCKET_BASE_URL)
            } catch (e: URISyntaxException) {
                throw RuntimeException(e)
            }
        }
        return mSocket
    }

    fun onRegister(observer: Observer) {
        if (observerList != null && !observerList!!.contains(observer)) {
            observerList!!.clear()
            observerList!!.add(observer)
        } else {
            observerList = ArrayList()
            observerList!!.clear()
            observerList!!.add(observer)
        }
    }

    fun unRegister(observer: Observer) {
        observerList?.let { list ->
            for (i in 0 until list.size - 1) {
                val model = list[i]
                if (model === observer) {
                    observerList?.remove(model)
                }
            }
        }
    }


    fun init() {
        initializeSocket()
    }

    private fun initializeSocket() {
        if (mSocket == null) {
            mSocket = getSocket()
        }
        if (observerList == null || observerList!!.size == 0) {
            observerList = ArrayList()
        }

        disconnect()
        mSocket!!.connect()
        mSocket!!.on(Socket.EVENT_CONNECT, onConnect)
        // mSocket!!.on(Socket.EVENT_DISCONNECT, onDisconnect)
        mSocket!!.on(Socket.EVENT_CONNECT_ERROR, onConnectError)
        // mSocket!!.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError)
        mSocket!!.on(errorMessage, onErrorMessage)

       /* mSocket!!.off(deliveryStatusListener)
        mSocket!!.on(deliveryStatusListener, onGettingMessageUpdate)*/

    }

    private fun disconnect() {
        if (mSocket != null) {
            // mSocket!!.off(Socket.EVENT_DISCONNECT, onDisconnect)
            mSocket!!.off(Socket.EVENT_CONNECT, onConnect)
            mSocket!!.off(Socket.EVENT_DISCONNECT, onDisconnect)
            mSocket!!.off(Socket.EVENT_CONNECT_ERROR, onConnectError)
            // mSocket!!.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError)
            mSocket!!.off()
            mSocket!!.disconnect()
        }
    }

    fun disconnectAll() {
        try {
            if (mSocket != null) {
                // mSocket!!.emit(Socket.EVENT_DISCONNECT, onDisconnect)
                mSocket!!.off(Socket.EVENT_CONNECT, onConnect)
                mSocket!!.off(Socket.EVENT_DISCONNECT, onDisconnect)
                mSocket!!.off(Socket.EVENT_CONNECT_ERROR, onConnectError)
                //mSocket!!.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError)
                mSocket!!.off()
                mSocket!!.disconnect()
            }
        } catch (e: Exception) {
        }
    }


    private val onConnect = Emitter.Listener {
        if (isConnected()) {
            try {
                val jsonObject = JSONObject()
                val userid = getPrefrence(Constants.USER_ID, "")

                if (userid.isNotEmpty()) {
                    if (userid.toInt() != 0) {
                        jsonObject.put("userId", userid.toInt())
                      //  mSocket!!.off(connect_listener, onConnectListener)
                      //  mSocket!!.on(connect_listener, onConnectListener)
                        mSocket!!.emit(CONNECT_USER, jsonObject)

                    }
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        } else {
            initializeSocket()
        }
    }

    fun isConnected(): Boolean {
        return mSocket != null && mSocket!!.connected()
    }

    private val onConnectListener = Emitter.Listener { args ->
        try {

            Log.e("Socket", "Connected" + args[0].toString())

            Log.e("Socket", "Connected" + args[0].toString())

            // val data = args[1] as JSONObject

            // val data = args[1] as JSONObject
        } catch (ex: Exception) {
            ex.localizedMessage
        }
    }

    private val onDisconnect = Emitter.Listener { args ->
        try {
            Log.e("Socket", "DISCONNECTED :::$args")
        } catch (ex: Exception) {
            ex.localizedMessage
        }
    }

    private val onConnectError = Emitter.Listener { args ->
        try {
            Log.e("Socket", "CONNECTION ERROR :::$args")
        } catch (ex: Exception) {
            ex.localizedMessage
        }
    }

    private val onErrorMessage = Emitter.Listener { args ->
        for (observer in observerList!!) {
            try {
                val data = args[0] as JSONObject
                Log.e("Socket", "Error Message :::$data")
                observer.onError(CONNECT_USER, args)
            } catch (ex: Exception) {
                ex.localizedMessage
            }
        }
    }


    fun sendMessage(jsonObject: JSONObject?) {
        if (jsonObject != null) {
            try {
                if (!mSocket!!.connected()) {
                    mSocket!!.connect()
                    mSocket!!.off(SEND_MESSAGE_LISTNER)
                    mSocket!!.on(SEND_MESSAGE_LISTNER, onNewMessage)
                    mSocket!!.emit(SEND_MESSAGE, jsonObject)
                } else {
                    mSocket!!.off(SEND_MESSAGE_LISTNER)
                    mSocket!!.on(SEND_MESSAGE_LISTNER, onNewMessage)
                    mSocket!!.emit(SEND_MESSAGE, jsonObject)
                }
            } catch (ex: Exception) {
                ex.localizedMessage
            }

            Log.i("Socket", "Send Message Called")
        }
    }


    fun callToUser(jsonObject: JSONObject?) {
        if (jsonObject != null) {
            try {
                if (!mSocket!!.connected()) {
                    mSocket!!.connect()

                    mSocket!!.emit(CALL_TO_USER, jsonObject)
                } else {

                    mSocket!!.emit(CALL_TO_USER, jsonObject)
                }
            } catch (ex: Exception) {
              //  ex.localizedMessage
            }

            Log.i("Socket", "Send Message Called")
        }
    }
    
    fun endCallSocket(jsonObject: JSONObject?) {
        if (jsonObject != null) {
            try {
                if (!mSocket!!.connected()) {
                    mSocket!!.connect()

                    mSocket!!.emit(END_CALL, jsonObject)
                } else {

                    mSocket!!.emit(END_CALL, jsonObject)
                }
            } catch (ex: Exception) {
              //  ex.localizedMessage
            }

            Log.i("Socket", "Send Message Called")
        }
    }


    private val onNewMessage = Emitter.Listener { args ->
        try {
            val data = args[0] as JSONObject
            Log.e("Socket", "onNewMessage :::$data")
            for (observer in observerList!!) {
                observer.onResponse(SEND_MESSAGE, data)
            }
        } catch (ex: Exception) {
            ex.localizedMessage
        }
    }


    fun receivedMessageActivate() {

        try {
            if (!mSocket!!.connected()) {
                mSocket!!.connect()
                mSocket!!.off(SEND_MESSAGE_LISTNER)
                mSocket!!.on(SEND_MESSAGE_LISTNER, onReceivedMessage)
            } else {
                mSocket!!.off(SEND_MESSAGE_LISTNER)
                mSocket!!.on(SEND_MESSAGE_LISTNER, onReceivedMessage)

            }
        } catch (ex: Exception) {
            ex.localizedMessage
        }

        Log.i("Socket", "received Message Called")

    }
    private val onReceivedMessage = Emitter.Listener { args ->
        try {
            val data = args[0] as JSONObject
            Log.e("Socket", "onGetFriendChat :::$data")
            for (observer in observerList!!) {
                observer.onResponse(SEND_MESSAGE_LISTNER, data)
            }
        } catch (ex: Exception) {
            ex.localizedMessage
        }
    }


    fun getFriendChat(jsonObject: JSONObject?) {
        if (jsonObject != null) {
            try {
                if (!mSocket!!.connected()) {
                    mSocket!!.connect()
                    mSocket!!.off(GET_CHAT_LISTNER_ONE_TO_ONE)
                    mSocket!!.on(GET_CHAT_LISTNER_ONE_TO_ONE, onGetFriendChat)
                    mSocket!!.emit(GET_CHAT_EMITTER, jsonObject)
                } else {
                    mSocket!!.off(GET_CHAT_LISTNER_ONE_TO_ONE)
                    mSocket!!.on(GET_CHAT_LISTNER_ONE_TO_ONE, onGetFriendChat)
                    mSocket!!.emit(GET_CHAT_EMITTER, jsonObject)
                }
            } catch (ex: Exception) {
                ex.localizedMessage
            }

            Log.i("Socket", "Send Message Called")
        }
    }

    // to get group chat list
    fun getGroupList(jsonObject: JSONObject?) {
        if (jsonObject != null) {
            try {
                if (!mSocket!!.connected()) {
                    mSocket!!.connect()

                    mSocket!!.emit(participantsList, jsonObject, Ack{ args->
                        Log.i("Socket", args.toString())
                    })
                } else {

                    mSocket!!.emit(participantsList, jsonObject,Ack{ args->

                        val data = args[0] as JSONArray
                        Log.e("Socket", "onGetFriendChat :::$data")
                        for (observer in observerList!!) {
                            observer.onResponseArray(participantsList, data)
                        }
                    })
                }
            } catch (ex: Exception) {
                ex.localizedMessage
            }

            Log.i("Socket", "Send Message Called")
        }
    }

    // to create the group
    // to get group chat list
    fun createGroup(jsonObject: JSONObject?) {
        if (jsonObject != null) {
            try {
                if (!mSocket!!.connected()) {
                    mSocket!!.connect()

                    mSocket!!.emit(createGroupEmitter, jsonObject, Ack{ args->
                        Log.i("Socket", args.toString())
                    })
                } else {

                    mSocket!!.emit(createGroupEmitter, jsonObject,Ack{ args->

                        val data = args[0] as JSONArray
                        Log.e("Socket", "onCreateGroup :::$data")
                        for (observer in observerList!!) {
                            observer.onResponseArray(createGroupEmitter, data)
                        }
                    })
                }
            } catch (ex: Exception) {
                ex.localizedMessage
            }

            Log.i("Socket", "Create Group Called")
        }
    }

    // method for join room
    fun joinRoom(jsonObject: JSONObject?) {
        if (jsonObject != null) {
            try {
                if (!mSocket!!.connected()) {
                    mSocket!!.connect()

                    mSocket!!.emit(joinRoomEmitter, jsonObject, Ack{ args->
                        Log.i("Socket", args.toString())
                    })
                } else {

                    mSocket!!.emit(joinRoomEmitter, jsonObject,Ack{ args->

                        val data = args[0] as JSONObject
                        Log.e("Socket", "onGetFriendChat :::$data")
                        for (observer in observerList!!) {
                            observer.onResponse(joinRoomEmitter, data)
                        }
                    })
                }
            } catch (ex: Exception) {
                ex.localizedMessage
            }

            Log.i("Socket", "joinRoom Called")
        }
    }

    private val onGetFriendChat = Emitter.Listener { args ->
        try {
            val data = args[0] as JSONArray
            Log.e("Socket", "onGetFriendChat :::$data")
            for (observer in observerList!!) {
                observer.onResponseArray(GET_CHAT_EMITTER, data)
            }
        } catch (ex: Exception) {
            ex.localizedMessage
        }
    }

    private val onGroupChat = Emitter.Listener { args ->
        try {
            val data = args[0] as JSONArray
            Log.e("Socket", "onGetFriendChat :::$data")
            for (observer in observerList!!) {
                observer.onResponseArray(participantsList, data)
            }
        } catch (ex: Exception) {
            ex.localizedMessage
        }
    }


  /*  fun sendMessageForChat(jsonObject: JSONObject?) {
        if (jsonObject != null) {
            try {
                if (!mSocket!!.connected()) {
                    mSocket!!.connect()
                    mSocket!!.off(send_message_listner)
                    mSocket!!.on(send_message_listner, onSendMessageListenerForChat)
                    mSocket!!.emit(send_message, jsonObject)
                } else {
                    mSocket!!.off(send_message_listner)
                    mSocket!!.on(send_message_listner, onSendMessageListenerForChat)
                    mSocket!!.emit(send_message, jsonObject)
                }
            } catch (ex: Exception) {
                ex.localizedMessage
            }

            Log.i("Socket", "Send Message Called")
        }
    }

    fun sendMessageListenerForActivate() {
        try {
            if (!mSocket!!.connected()) {
                mSocket!!.connect()
                mSocket!!.off(send_message_listner)
                mSocket!!.on(send_message_listner, onSendMessageListenerForChat)
            } else {
                mSocket!!.off(send_message_listner)
                mSocket!!.on(send_message_listner, onSendMessageListenerForChat)
            }
        } catch (ex: Exception) {
            ex.localizedMessage
        }


    }*/

  /*  private val onSendMessageListenerForChat = Emitter.Listener { args ->
        try {
            val data = args[0] as JSONObject
            Log.e("Socket", "sendMessage :::$data")
            for (observer in observerList!!) {
                observer.onResponse(send_message, data)
            }
        } catch (ex: Exception) {
            ex.localizedMessage
        }
    }
*/

    fun getChatList(jsonObject: JSONObject?) {
        if (jsonObject != null) {
            try {
                if (!mSocket!!.connected()) {
                    mSocket!!.connect()
                    mSocket!!.off(CHAT_LISTING_LISTNER)
                    mSocket!!.on(CHAT_LISTING_LISTNER, chatListListener)
                    mSocket!!.emit(CHAT_LISTING_EMITTER, jsonObject)
                } else {
                    mSocket!!.off(CHAT_LISTING_LISTNER)
                    mSocket!!.on(CHAT_LISTING_LISTNER, chatListListener)
                    mSocket!!.emit(CHAT_LISTING_EMITTER, jsonObject)
                }
            } catch (ex: Exception) {
                ex.localizedMessage
            }

            Log.i("Socket", "Send Message Called")
        }
    }

    private val chatListListener = Emitter.Listener { args ->
        try {
            val data = args[0] as JSONArray
            Log.e("Socket", "chatList :::$data")
            for (observer in observerList!!) {
                observer.onResponseArray(CHAT_LISTING_EMITTER, data)
            }
        } catch (ex: Exception) {
            ex.localizedMessage
        }
    }




    interface Observer {
        fun onResponseArray(event: String, args: JSONArray)
        fun onResponse(event: String, args: JSONObject)
        fun onError(event: String, vararg args: Array<*>)
    }
    fun receiveMsgListener() {
        try {
            if (!mSocket!!.connected()) {
                mSocket!!.connect()
                mSocket!!.off(SEND_MESSAGE_LISTNER)
                mSocket!!.on(SEND_MESSAGE_LISTNER, onReceiverMsgListener)

            } else {
                mSocket!!.off(SEND_MESSAGE_LISTNER)
                mSocket!!.on(SEND_MESSAGE_LISTNER, onReceiverMsgListener)


            }
        } catch (ex: Exception) {
            ex.localizedMessage
        }

    }
    private val onReceiverMsgListener = Emitter.Listener { args ->
        try {
            val data = args[0] as JSONObject
            Log.d("SocketListener", "CallStatusList :::$data")
            for (observer in observerList!!) {
                observer.onResponse(SEND_MESSAGE_LISTNER, data)
            }
        } catch (ex: Exception) {
            ex.localizedMessage
        }

    }
}