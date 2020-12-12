package com.kotlindemo.common

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

class AlarmReceiver : BroadcastReceiver() {
    var TAG = javaClass.simpleName
    var context: Context? = null

    override fun onReceive(context: Context, intent: Intent) {
        this.context = context
        Log.e(TAG, "onReceive: ")
        setAlarmToApp()
    }

    private fun setAlarmToApp() {
        val alarmMgr = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.SECOND, 20)
        val date = SimpleDateFormat("dd-MM-yyyy hh:mm:ss a").format(calendar.time)
        showToast(context!!, "Set Alarm at : $date")
        Log.e(TAG, "setAlarmToApp: at : $date")
        val delayInMill = calendar.timeInMillis
        //        if (alarmMgr != null) {
        if (Build.VERSION.SDK_INT >= 23) {
            alarmMgr.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, delayInMill, alarmIntent)
        } else {
            alarmMgr.setExact(AlarmManager.RTC_WAKEUP, delayInMill, alarmIntent)
        }
        //        }
    }
}