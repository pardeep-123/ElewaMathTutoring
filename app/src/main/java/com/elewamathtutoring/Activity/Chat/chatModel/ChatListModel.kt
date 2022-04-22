package com.elewamathtutoring.Activity.Chat.chatModel
import com.google.gson.annotations.SerializedName


//class ChatListModel : ArrayList<ChatListModel.ChatListItem>(){

    /*
    {"id":294,
    "SenderName":"KUMAR","message":"1647955688.jpeg"
    ,"SenderID":352,"SenderImage":"16479547082.jpg",
    "msg_type":1,"createdAt":1647955689}
     */
//}

 class ChatListModel {
     @SerializedName("get_data_chat")
     val getDataChat: ArrayList<ChatListItem>?=null

     @SerializedName("isBooked")
     val isBooked: String?=null

     data class ChatListItem(
         @SerializedName("createdAt")
         val createdAt: String, // 1647953539
         @SerializedName("id")
         val id: Int, // 279
         @SerializedName("message")
         val message: String, // hiii
         @SerializedName("msgType")
         val msgType: Int, // 0
         @SerializedName("ReceiverId")
         val receiverId: Int, // 350
         @SerializedName("ReceiverImage")
         val receiverImage: String, // 164725147020220105_150455000_cb8bcf07f7ad46a9f84491a0f955e5ba.jpg
         @SerializedName("ReceiverName")
         val receiverName: String, // student
         @SerializedName("SenderID")
         val senderID: Int, // 352
         @SerializedName("SenderImage")
         val senderImage: String,
         @SerializedName("SenderName")
         val senderName: String // KUMAR
     )
 }
