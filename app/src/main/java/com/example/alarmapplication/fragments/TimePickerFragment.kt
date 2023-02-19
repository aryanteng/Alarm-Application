package com.example.alarmapplication.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.alarmapplication.AlarmService
import com.example.alarmapplication.databinding.FragmentTimePickerBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TimePickerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TimePickerFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var timePickerBinding: FragmentTimePickerBinding
    private var list: MutableList<HashMap<String, Int>> = mutableListOf()
    private lateinit var alarms: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        timePickerBinding = FragmentTimePickerBinding.inflate(inflater, container, false)
        alarms = "Your Alarms: \n"
        timePickerBinding.btnStart.setOnClickListener {
            val hash: HashMap<String, Int> = hashMapOf()
            val intent = Intent(activity, AlarmService::class.java)
            hash["hours"] =  timePickerBinding.timePicker.hour
            hash["minutes"] = timePickerBinding.timePicker.minute
            if(!list.contains(hash)){
                list.add(hash)
                intent.putExtra("hash", hash)
                activity?.startService(intent)
                showAlarmsUI(hash)
            }
            else{
                Toast.makeText(activity, "Alarm Already Set!", Toast.LENGTH_SHORT).show()
            }
        }

        timePickerBinding.btnStop.setOnClickListener {
            activity?.stopService(Intent(activity, AlarmService::class.java))
            alarms = "Your Alarms:\n"
            timePickerBinding.tvAlarmList.text = null
            list.clear()
        }

        return timePickerBinding.root
    }

    private fun showAlarmsUI(hash: HashMap<String, Int>){
        Log.i("ALARM LIST 3", list.toString())
        var hours = hash["hours"]
        val minutes = hash["minutes"]
        var string = ""
        if (hours != null) {
            if(hours > 12){
                hours -= 12
                if (minutes != null) {
                    string = if(minutes < 10){
                        "$hours:0$minutes PM\n"
                    } else{
                        "$hours:$minutes PM\n"
                    }
                }
            }
            else{
                if (minutes != null) {
                    string = if(minutes < 10){
                        "$hours:0$minutes AM\n"
                    } else{
                        "$hours:$minutes AM\n"
                    }
                }
            }
        }
        alarms += string
        timePickerBinding.tvAlarmList.text = alarms
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TimePicker.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TimePickerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}