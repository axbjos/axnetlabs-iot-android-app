package com.axnetlabs.basiciotapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.view.View
import com.android.volley.*
import com.android.volley.toolbox.*

//import org.json.JSONObject
//import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var sensorToRetrieve: EditText
    private lateinit var sensorData: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sensorToRetrieve = findViewById(R.id.sensorNum)
        sensorData = findViewById(R.id.sensorData)
    }

    fun  retrieveData(view: View){
        val sensorId = sensorToRetrieve.text.toString()
        //sensorData.setText("Retrieving Data for Sensor: " + sensorId)
         // Instantiate the cache
        val cache = DiskBasedCache(cacheDir, 1024 * 1024) // 1MB cap
        // Set up the network to use HttpURLConnection as the HTTP client.
        val network = BasicNetwork(HurlStack())

        //val queue = Volley.newRequestQueue(this)
        val requestQueue = RequestQueue(cache, network).apply {
            start()
        }

        val url = "http://172.16.141.133:1880/ui/?sensor_no=" + sensorId
        //val url = "https://www.google.com"

        // Formulate the request and handle the response.
        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                sensorData.text = response// Do something with the response
            },
            { error ->
                // Handle error
                sensorData.text = "ERROR: %s".format(error.toString())
            })

    // Add the request to the RequestQueue.
        requestQueue.add(stringRequest)

    }
}