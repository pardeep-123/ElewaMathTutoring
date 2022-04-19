package com.elewamathtutoring.Util.fcm

import java.io.Serializable

data class PushNotificationModel(
    val userId: String?,
    val message: String,
    val title: String,
    val friendId: String,
    val friendName: String,
    val userName: String,
    val userImage: String,
    val user2Id: String?,
    val friendImage: String,
    val callid: String,
    val sessionId: String,
    val groupId: String?,
    val channelName: String?,
    val token: String?,
    val type: String?,
//    val name: String,
//    val notification_code: String,
//    val product_id: String,
//    val receiverid: String,
    val push_type: String

    /*
    {
"title": "Tayyab",
"push_type": 17,
"message": " Incoming voice call ",
"userId": 233,
"userName": "Tayyab",
"userImage": "1637763798swift_file11_24_21_19:23:15.23.jpeg",
"friendId": 230,
"friendName": "deep",
"friendImage": "1637306192swift_file11_19_21_12:46:30.89.jpeg",
"token": "00694d43cc0cd50466190e2cb017b6453ddIADtjSQVvauXK6cMQ8XK1tLuANehoeJQCr6ku4ClbUyPKkSdOuQAAAAAEADfj2mOCtpfYgEAAQAAAAAA",
"channelName": "ncxjf06VuK8VA7hV52LV"
}
aa hi aayega
     */
):Serializable