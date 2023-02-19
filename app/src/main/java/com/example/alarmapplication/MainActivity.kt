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
    private lateinit var incomingCallReceiver: IncomingCallReceiver
    private lateinit var powerConnectedReceiver: PowerConnectedReceiver
    private lateinit var timePickerFragment: TimePickerFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        batteryLowReceiver = BatteryLowReceiver()
        batteryOkayReceiver = BatteryOkayReceiver()
        incomingCallReceiver = IncomingCallReceiver()
        powerConnectedReceiver = PowerConnectedReceiver()

        timePickerFragment = supportFragmentManager.findFragmentByTag("timePicker") as TimePickerFragment

        IntentFilter(Intent.ACTION_BATTERY_LOW).also {
            registerReceiver(batteryLowReceiver, it)
        }

        IntentFilter(Intent.ACTION_BATTERY_OKAY).also {
            registerReceiver(batteryOkayReceiver, it)
        }

        IntentFilter(Intent.ACTION_POWER_CONNECTED).also {
            registerReceiver(powerConnectedReceiver, it)
        }

        IntentFilter(Intent.ACTION_CALL).also {
            registerReceiver(incomingCallReceiver, it)
        }

    }

}