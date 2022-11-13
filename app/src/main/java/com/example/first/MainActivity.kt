package com.example.first

import com.example.first.BasicActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast

const val WINDOW_NAME_PARAM = "com.first.windowname.attribute"

class MainActivity : BasicActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    /** Called when the user taps the button */
    fun openWindow(view: View) {
        // Extract value filled in editext identified with txt_window_name id
        val windowName = "sd"
        // Display a message
        Toast.makeText(this, "You choose $windowName", Toast.LENGTH_LONG).show()
        // Do something in response to button
        val intent = Intent(this, WindowActivity::class.java).apply {
            putExtra(WINDOW_NAME_PARAM, windowName)
        }
        startActivity(intent)
    }
}