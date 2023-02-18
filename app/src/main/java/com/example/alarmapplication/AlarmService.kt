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
    private var player: MediaPlayer = MediaPlayer()
    private lateinit var timer: Timer
    private var list: MutableList<HashMap<String, Int>> = mutableListOf()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        logCallback("Service Started")
        val hash = intent?.getSerializableExtra("hash") as HashMap<String, Int>
        list.add(hash)
        timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                Log.i("list", list.toString())
                val calendar = Calendar.getInstance()
                val currHour = calendar.get(Calendar.HOUR_OF_DAY)
                val currMinute = calendar.get(Calendar.MINUTE)
                list.forEach {
                    if(it["minutes"] == currMinute && it["hours"] == currHour){
//                    logCallback("Alarm Ringing!")
                        startSound()
                        Log.i("list", list.toString())
                        list.remove(it)
                        timer.schedule(object : TimerTask(){
                            override fun run() {
                                stopSound()
                            }
                        }, 10000)
                        return
                    }
                }
            }
        }, 0, 10000)

        return START_STICKY
    }

    private fun startSound(){
        player = MediaPlayer.create(this@AlarmService, Settings.System.DEFAULT_ALARM_ALERT_URI)
        player.start()
    }

    private fun stopSound(){
        player.stop()
        player.release()
    }

    override fun onDestroy() {
        super.onDestroy()
        logCallback("Service Stopped")
        stopSound()
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