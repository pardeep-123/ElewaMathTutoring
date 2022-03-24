package com.elewamathtutoring.Activity.Chat.chatModel.chatThreads


import com.google.gson.annotations.SerializedName

class GetMessageInboxModel : ArrayList<GetMessageInboxModel.GetMessageInboxModelItem>(){
    data class GetMessageInboxModelItem(
        @SerializedName("blockstatus")
        val blockstatus: Int, // 0
        @SerializedName("created")
        val created: Int, // 1647953539
        @SerializedName("createdAt")
        val createdAt: String, // 2022-03-22T12:52:18.000Z
        @SerializedName("deletedId")
        val deletedId: Int, // 0
        @SerializedName("groupId")
        val groupId: Int?,
        @SerializedName("groupImage")
        val groupImage: String, // 1648122475.jpeg
        @SerializedName("groupName")
        val groupName: String?, // Funny// null
        @SerializedName("id")
        val id: Int, // 22
        @SerializedName("lastMessage")
        val lastMessage: String, // hiii
        @SerializedName("lastMsgId")
        val lastMsgId: Int, // 279
        @SerializedName("msg_type")
        val msgType: Int, // 0
        @SerializedName("onlinestatus")
        val onlinestatus: Int, // 1
        @SerializedName("typing")
        val typing: Int, // 0
        @SerializedName("unreadcount")
        val unreadcount: Int, // 0
        @SerializedName("updatedAt")
        val updatedAt: String, // 2022-03-22T12:52:18.000Z
        @SerializedName("user2Id")
        val user2Id: Int, // 350
        @SerializedName("user_id")
        val userId: Int, // 350
        @SerializedName("userImage")
        val userImage: String, // 164725147020220105_150455000_cb8bcf07f7ad46a9f84491a0f955e5ba.jpg
        @SerializedName("userName")
        val userName: String, // student
        @SerializedName("userid")
        val userid: Int // 352
    )
}
