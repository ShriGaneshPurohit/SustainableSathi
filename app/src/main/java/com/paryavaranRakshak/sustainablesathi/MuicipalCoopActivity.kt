package com.paryavaranRakshak.sustainablesathi

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.paryavaranRakshak.sustainablesathi.Interface.InterfaceData
import com.paryavaranRakshak.sustainablesathi.databinding.ActivityMuicipalCoopBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MuicipalCoopActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMuicipalCoopBinding
    private var cityName: String? = null
    private lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMuicipalCoopBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressDialog =  ProgressDialog(this)
        progressDialog.setMessage("Creating Route....")

        // Setting up categories
        val state = resources.getStringArray(R.array.state)
        val statearrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, state)
        binding.etState.adapter = statearrayAdapter

        val city = resources.getStringArray(R.array.city)
        val cityarrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, city)
        binding.etCity.adapter = cityarrayAdapter

        // Spinner item selection listener
        binding.etCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                cityName = city[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle the case where nothing is selected, if needed
            }
        }

        binding.btnMap.setOnClickListener { get(cityName!!) }
    }

    private fun get(city: String) {
        progressDialog.show()
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://sustainable-sathi.tech/backend/api/") // Replace with your actual base URL
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(InterfaceData::class.java)

                val call = retrofit.getLocationMod(city)

                val response = call.execute()

                if (response.isSuccessful) {
                    val apiResponse = response.body()

                    if (apiResponse != null) {
                        val coordinatesList = mutableListOf<String>()

                        for (sellerData in apiResponse.result) {
                            val coordinateString = "${sellerData.latitude},${sellerData.longitude}"
                            coordinatesList.add(coordinateString)
                        }

                        val url =
                            "https://www.google.com/maps/dir/" + coordinatesList.joinToString("/")
                        println("Final URL: $url")

                        // Create an intent with ACTION_VIEW and the external link URI

                        // Create an intent with ACTION_VIEW and the external link URI
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

                        // Start the intent

                        // Start the intent
                        startActivity(intent)
                        progressDialog.dismiss()
                    } else {
                        println("Empty response body")
                    }
                } else {
                    println("API request failed with code ${response.code()}")
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }
}
