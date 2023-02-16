package com.example.alarmapplication

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import java.util.Calendar
import java.util.Date

private var TAG = "AlarmService"

class AlarmService : Service() {
    private lateinit var player: MediaPlayer

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        player = MediaPlayer.create(this, Settings.System.DEFAULT_ALARM_ALERT_URI)
        logCallback("Service Started")
        val currentTime: Date = Calendar.getInstance().time
        val currentHour: Int = Calendar.getInstance().time.hours
        val currentMinutes: Int = Calendar.getInstance().time.minutes
        val hours = intent?.getIntExtra("hours", 0)
        val minutes = intent?.getIntExtra("minutes", 0)
        Log.i(TAG, hours.toString())
        player.start()
        return START_STICKY
    }


    override fun onDestroy() {
        super.onDestroy()
        logCallback("Service Stopped")
        player.stop()
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    private fun logCallback(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        Log.i(TAG, message)
    }
}

