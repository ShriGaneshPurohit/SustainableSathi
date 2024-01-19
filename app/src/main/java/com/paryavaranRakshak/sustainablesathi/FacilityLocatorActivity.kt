package com.paryavaranRakshak.sustainablesathi

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.paryavaranRakshak.sustainablesathi.Interface.InterfaceData
import com.paryavaranRakshak.sustainablesathi.adapter.NearestFacilityAdapter
import com.paryavaranRakshak.sustainablesathi.databinding.ActivityFacilityLocatorBinding
import com.paryavaranRakshak.sustainablesathi.models.LocatorFacilityModel
import com.paryavaranRakshak.sustainablesathi.other.LoginSharedPreferenceHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FacilityLocatorActivity : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivityFacilityLocatorBinding

    //adapter
    private lateinit var Adapter: NearestFacilityAdapter

    //Base URL for the API
    private val baseApi = "https://sustainable-sathi.000webhostapp.com/seller/"

    //shared pref
    private lateinit var sharedPreferencesHelper: LoginSharedPreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFacilityLocatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize SharedPreferencesHelper
        sharedPreferencesHelper = LoginSharedPreferenceHelper(this)

        binding.progressBar.visibility = View.VISIBLE
        binding.rvFacility.visibility = View.INVISIBLE

        binding.rvFacility.layoutManager = LinearLayoutManager(this)
        getFacility()

    }

    private fun getFacility() {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseApi)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(InterfaceData::class.java)

        val call = retrofit.getFacility(sharedPreferencesHelper.getUid()!!)

        call.enqueue(object : Callback<List<LocatorFacilityModel>> {
            override fun onResponse(
                call: Call<List<LocatorFacilityModel>>,
                response: retrofit2.Response<List<LocatorFacilityModel>>
            ) {
                val data = response.body()!!
                Adapter = NearestFacilityAdapter(this@FacilityLocatorActivity, data)
                binding.rvFacility.adapter = Adapter

                if (Adapter.itemCount>0){
                    binding.progressBar.visibility = View.GONE
                    binding.rvFacility.visibility = View.VISIBLE
                }

            }

            override fun onFailure(call: Call<List<LocatorFacilityModel>>, t: Throwable) {
                println(t.message)
            }
        })
    }
}