package com.example.alarmapplication

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.alarmapplication.databinding.ActivityMainBinding
import com.example.alarmapplication.fragments.TimePickerFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var batteryLowReceiver: BatteryLowReceiver
    private lateinit var batteryOkayReceiver: BatteryOkayReceiver
    private lateinit var timePickerFragment: TimePickerFragment
    private var hash: HashMap<String, Int> = hashMapOf()
    private var list: MutableList<HashMap<String, Int>> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        batteryLowReceiver = BatteryLowReceiver()
        batteryOkayReceiver = BatteryOkayReceiver()

        timePickerFragment = supportFragmentManager.findFragmentByTag("timePicker") as TimePickerFragment

        IntentFilter(Intent.ACTION_BATTERY_LOW).also {
            registerReceiver(batteryLowReceiver, it)
        }

        IntentFilter(Intent.ACTION_BATTERY_OKAY).also {
            registerReceiver(batteryOkayReceiver, it)
        }

        binding.btnStart.setOnClickListener {
            val intent = Intent(this, AlarmService::class.java)
            hash["hours"] =  timePickerFragment.getHour()
            hash["minutes"] = timePickerFragment.getMinute()
            list.add(hash)
            intent.putExtra("hash", hash)
            startService(intent)
        }

        binding.btnStop.setOnClickListener {
            stopService(Intent(this, AlarmService::class.java))
        }

    }

}