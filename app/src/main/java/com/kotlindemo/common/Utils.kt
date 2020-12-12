package com.kotlindemo.common

import android.content.Context
import android.util.Log
import android.widget.Toast
import java.io.*

fun showToast(context: Context,msg: String) {
    Toast.makeText(context,msg,Toast.LENGTH_LONG).show()
}

fun Any.toast(context: Context, duration: Int = Toast.LENGTH_SHORT): Toast {
    return Toast.makeText(context, this.toString(), duration).apply { show() }
}

fun copyFile(
    inputPath: String?,
    outputPath: String,
    fileName: String
): String? {
        Log.e("TAG", "file inputPath : " + inputPath);
    Log.e("TAG", "file outputPath : " + outputPath);
    var `in`: InputStream? = null
    var out: OutputStream? = null
    var path = ""
    try {
        //create output directory if it doesn't exist
        val dir = File(outputPath)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        `in` = FileInputStream(inputPath)
        out = FileOutputStream("$outputPath/$fileName")
        val buffer = ByteArray(1024)
        var read: Int
        while (`in`.read(buffer).also { read = it } != -1) {
            out.write(buffer, 0, read)
        }
        `in`.close()
        `in` = null
        // write the output file (You have now copied the file)
        out.flush()
        out.close()
        out = null
        path = "$outputPath/$fileName"
    } catch (e: Exception) {
        Log.e("TAG", "copyFile: exe "+e.message )
        path = ""
    }
    return path
}

