package com.example.alarmapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.alarmapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)



        binding.btnStart.setOnClickListener {
            val intent = Intent(this, AlarmService::class.java)
            startService(intent)
            intent.putExtra("hours", 12)
            intent.putExtra("minutes", 55)
        }

        binding.btnStop.setOnClickListener {
            stopService(Intent(this, AlarmService::class.java))
        }


    }
}