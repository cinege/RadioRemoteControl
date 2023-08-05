package com.example.radio

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import com.example.radio.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private var button0: Button? = null
    private var button1: Button? = null
    private var button2: Button? = null
    private var button3: Button? = null
    private var button4: Button? = null
    private var button5: Button? = null
    private var button6: Button? = null
    private var button7: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button0 = findViewById(R.id.button0)
        button0?.setOnClickListener{send_POST("0")}
        button1 = findViewById(R.id.button1)
        button1?.setOnClickListener{send_POST("1")}
        button2 = findViewById(R.id.button2)
        button2?.setOnClickListener{send_POST("2")}
        button3 = findViewById(R.id.button3)
        button3?.setOnClickListener{send_POST("3")}
        button4 = findViewById(R.id.button4)
        button4?.setOnClickListener{send_POST("4")}
        button5 = findViewById(R.id.button5)
        button5?.setOnClickListener{send_POST("5")}
        button6 = findViewById(R.id.button6)
        button6?.setOnClickListener{send_POST("100")}
        button7 = findViewById(R.id.button7)
        button7?.setOnClickListener{send_POST("200")}

    }

    fun send_POST(index: String) {
        lifecycleScope.launch(Dispatchers.Default) {
            val urlstr = "http://192.168.100.19:8080"
            val url = URL(urlstr)
            val postData = index
            try {
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "POST"
                conn.doOutput = true
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
                conn.setRequestProperty("Content-Length", postData.length.toString())
                conn.useCaches = false

                DataOutputStream(conn.outputStream).use { it.writeBytes(postData) }
                BufferedReader(InputStreamReader(conn.inputStream)).use { br ->
                    var line: String?
                    while (br.readLine().also { line = it } != null) {
                        println(line)
                    }
                }
            } catch (e: Exception) {
                print("Error")
            }
        }
    }
}