package com.kotlindemo.common

import android.app.Activity
import android.content.Context
import android.os.Looper
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.kotlindemo.main.HomeActivity
import java.util.logging.Handler

class MyWork(ctx: Context, workerParams: WorkerParameters) : Worker(ctx, workerParams) {

    var context: Context
    init {
        context = ctx
    }

    val TAG = MyWork::class.java.simpleName
    override fun doWork(): Result {
        Log.e(TAG, "doWork: MyWork start ")
      android.os.Handler(Looper.getMainLooper()).post({
          showToast(context, "MyWork start")
      })
        Thread(Runnable {
            for (i in 0..9) {
                Log.e(TAG, "run: $i")
                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }).start()
        return Result.success()
    }

    override fun onStopped() {
        super.onStopped()
        Log.e(TAG, "onStopped: called" )
    }

}