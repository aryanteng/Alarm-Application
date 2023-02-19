package com.example.alarmapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class IncomingCallReceiver : BroadcastReceiver() {

    override fun onReceive(p0: Context?, p1: Intent?) {

        val isIncomingCall = p1?.getBooleanExtra("state", false) ?: return

        if(isIncomingCall){
            p0?.stopService(Intent(p0, AlarmService::class.java))
        }

    }

}