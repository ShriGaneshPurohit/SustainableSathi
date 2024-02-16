package com.paryavaranRakshak.sustainablesathi.seller.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.paryavaranRakshak.sustainablesathi.Interface.InterfaceData
import com.paryavaranRakshak.sustainablesathi.R
import com.paryavaranRakshak.sustainablesathi.databinding.FragmentFacilityLocatorBinding
import com.paryavaranRakshak.sustainablesathi.other.LoginSharedPreferenceHelper
import com.paryavaranRakshak.sustainablesathi.seller.adapter.NearestFacilityAdapter
import com.paryavaranRakshak.sustainablesathi.seller.models.LocatorFacilityModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FacilityLocatorFragment : Fragment() {

    //view binding
    private lateinit var binding: FragmentFacilityLocatorBinding

    //adapter
    private lateinit var Adapter: NearestFacilityAdapter

    //Base URL for the API
    private val baseApi = "https://sustainable-sathi.tech/backend/api/seller/"

    //shared pref
    private lateinit var sharedPreferencesHelper: LoginSharedPreferenceHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFacilityLocatorBinding.inflate(layoutInflater)

        // Initialize SharedPreferencesHelper
        sharedPreferencesHelper = LoginSharedPreferenceHelper(requireContext())

        binding.progressBar.visibility = View.VISIBLE
        binding.rvFacility.visibility = View.INVISIBLE

        binding.rvFacility.layoutManager = LinearLayoutManager(requireContext())
        getFacility()

        return binding.root
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
                Adapter = NearestFacilityAdapter(requireContext(), data)
                binding.rvFacility.adapter = Adapter

                if (Adapter.itemCount>0){
                    binding.progressBar.visibility = View.GONE
                    binding.rvFacility.visibility = View.VISIBLE
                    binding.tvFacilityCount.text = "${Adapter.itemCount} Facilities Found In 200KM Radius"
                }

            }

            override fun onFailure(call: Call<List<LocatorFacilityModel>>, t: Throwable) {
                println(t.message)
            }
        })
    }

}