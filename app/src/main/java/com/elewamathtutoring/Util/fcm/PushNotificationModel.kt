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
//    val name: String,
//    val notification_code: String,
//    val product_id: String,
//    val receiverid: String,
    val push_type: String
):Serializable