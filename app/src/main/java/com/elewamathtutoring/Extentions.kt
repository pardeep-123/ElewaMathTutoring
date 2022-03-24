package com.elewamathtutoring

import android.app.Activity
import android.widget.Toast
import java.io.File
import java.io.FileInputStream
import java.io.IOException

// function for convert image to bit 64

fun getBase64FromPath(path: String): String {
    var base64 = ""
    try {
        val file = File(path)
        val buffer = ByteArray(file.length().toInt() + 100)
        val length = FileInputStream(file).read(buffer)
        base64 = android.util.Base64.encodeToString(
            buffer, 0, length,
            android.util.Base64.DEFAULT
        )

    } catch (e: IOException) {
//e.printStackTrace()
    }
    return base64
}

fun Activity.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}