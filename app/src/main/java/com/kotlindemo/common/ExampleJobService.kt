package com.kotlindemo.common

import android.annotation.SuppressLint
import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log
import android.widget.Toast
import java.sql.Date
import java.text.SimpleDateFormat

@SuppressLint("NewApi") class ExampleJobService : JobService() {

    val TAG = ExampleJobService::class.java.simpleName
    private var jobCancelled = false

    override fun onStartJob(params: JobParameters?): Boolean {
        val date = SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(java.util.Date())
        Log.e(TAG, "onStartJob ----: " + date)
        Toast.makeText(this, "Start job --> " + date, Toast.LENGTH_SHORT).show()
        doBackgroundWork(params);
        return true
    }

    @SuppressLint("NewApi") private fun doBackgroundWork(params: JobParameters?) {
        Log.e(TAG, "doBackgroundWork: called ------")
        Thread(Runnable {
            for (i in 0..9) {
                Log.e(TAG, "run: $i")
                if (jobCancelled) {
                    return@Runnable
                }
                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            // Log.e(TAG, "Job finished")
             jobFinished(params, false)
        }).start()
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        Log.e(TAG, "onStopJob:")
        jobCancelled = true;
        return true
    }

}