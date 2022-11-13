package com.example.first

import com.example.first.BasicActivity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.first.model.WindowService

class WindowDetailActivity : BasicActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_window_detail)

        val id = intent.getLongExtra(WINDOW_NAME_PARAM, 0)
        val window = WindowService.findById(id)

        if (window != null) {
            findViewById<TextView>(R.id.txt_window_name).text = window.name
            findViewById<TextView>(R.id.txt_room_name).text = window.room.name
            findViewById<TextView>(R.id.txt_window_current_temperature).text = window.room.currentTemperature?.toString()
            findViewById<TextView>(R.id.txt_window_target_temperature).text = window.room.targetTemperature?.toString()
            findViewById<TextView>(R.id.txt_window_status).text = window.status.toString()
        }
    }
}