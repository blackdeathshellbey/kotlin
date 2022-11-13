package com.example.first

import com.example.first.BasicActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.first.model.ApiServices
import com.example.first.model.WindowAdapter
import com.example.first.model.WindowService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface OnWindowSelectedListener {
    fun onWindowSelected(id: Long)
}

class WindowActivity : BasicActivity(), OnWindowSelectedListener {

    val windowService = WindowService() // (1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_window)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //val param = intent.getStringExtra(WINDOW_NAME_PARAM)
        //val windowName = findViewById<TextView>(R.id.txt_window_name)
        //windowName.text = param

        val recyclerView = findViewById<RecyclerView>(R.id.list) // (2)
        val adapter = WindowAdapter(this) // (3)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

       // adapter.update(windowService.findAll()) // (4)

        lifecycleScope.launch(context = Dispatchers.IO) { // (1)
            runCatching { ApiServices().windowsApiService.findAll().execute() } // (2)
                .onSuccess {
                    withContext(context = Dispatchers.Main) { // (3)
                        adapter.update(it.body() ?: emptyList())
                    }

                }
                .onFailure {
                    withContext(context = Dispatchers.Main) { // (3)
                        Toast.makeText(
                            applicationContext,
                            "Error on windows loading $it",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }


    override fun onWindowSelected(id: Long) {
        val intent = Intent(this, WindowDetailActivity::class.java).putExtra(WINDOW_NAME_PARAM, id)
        startActivity(intent)
    }
}