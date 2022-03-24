package com.elewamathtutoring.Util.fcm

import java.io.Serializable

data class PushNotificationModel(
    val userId: String,
    val message: String,
//    val name: String,
//    val notification_code: String,
//    val product_id: String,
//    val receiverid: String,
    val push_type: String
):Serializable