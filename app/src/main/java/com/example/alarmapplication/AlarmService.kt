package com.example.alarmapplication

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import java.util.Calendar
import java.util.Timer
import java.util.TimerTask

private var TAG = "AlarmService"

class AlarmService : Service() {
    private lateinit var player: MediaPlayer
    private lateinit var timer: Timer

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        player = MediaPlayer.create(this, Settings.System.DEFAULT_ALARM_ALERT_URI)
        logCallback("Service Started")
        val hours = intent?.getIntExtra("hours", 0)
        val minutes = intent?.getIntExtra("minutes", 0)
        Log.i(TAG, hours.toString())
        Log.i(TAG, minutes.toString())
        timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val calendar = Calendar.getInstance()
                val currHour = calendar.get(Calendar.HOUR_OF_DAY)
                val currMinute = calendar.get(Calendar.MINUTE)
                if(minutes == currMinute && hours == currHour){
//                    logCallback("Alarm Ringing!")
                    player.start()
                    timer.schedule(object : TimerTask(){
                        override fun run() {
                            stopSelf()
                        }
                    }, 10000)
                }
            }
        }, 0, 10000)

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        logCallback("Service Stopped")
        player.stop()
        timer.cancel()
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    private fun logCallback(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        Log.i(TAG, message)
    }
}


