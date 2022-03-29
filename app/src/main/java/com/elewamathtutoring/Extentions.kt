package com.elewamathtutoring

import android.app.Activity
import android.widget.Toast
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.zip.Deflater

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

fun newRetrun(path: String) : String{
    var compressedString = ""
    val bytes: ByteArray = path.toByteArray(charset("UTF-8"))
    val deflater = Deflater(1, true)
    deflater.setInput(bytes)
    deflater.finish()

    val outputStream = ByteArrayOutputStream(bytes.size)

    try {
        val bytesCompressed = ByteArray(Short.MAX_VALUE.toInt())
        val numberOfBytesAfterCompression = deflater.deflate(bytesCompressed)
        val returnValues = ByteArray(numberOfBytesAfterCompression)
        System.arraycopy(bytesCompressed, 0, returnValues, 0, numberOfBytesAfterCompression)

        compressedString = android.util.Base64.encodeToString(returnValues, android.util.Base64.DEFAULT)
    } catch (e: IOException) {
        e.printStackTrace()
    }  finally {
        deflater.end()
        outputStream.close()
    }
    return compressedString
}
fun Activity.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}