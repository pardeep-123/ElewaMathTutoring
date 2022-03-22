package com.elewamathtutoring.Util.fcm

import java.io.Serializable

data class PushNotificationModel(
    val id: String,
    val message: String,
    val name: String,
    val notification_code: String,
    val product_id: String,
    val receiverid: String,
    val user_type: String
):Serializable