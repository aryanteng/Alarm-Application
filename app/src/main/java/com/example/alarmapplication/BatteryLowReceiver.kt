package com.example.alarmapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BatteryLowReceiver : BroadcastReceiver() {

    override fun onReceive(p0: Context?, p1: Intent?) {

        val isBatteryLow = p1?.getBooleanExtra("state", false) ?: return

        if(isBatteryLow){
            p0?.stopService(Intent(p0, AlarmService::class.java))
        }

    }

}