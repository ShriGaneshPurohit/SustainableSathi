package com.paryavaranRakshak.sustainablesathi.seller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.firebase.auth.FirebaseAuth
import com.paryavaranRakshak.sustainablesathi.Interface.InterfaceData
import com.paryavaranRakshak.sustainablesathi.R
import com.paryavaranRakshak.sustainablesathi.credits
import com.paryavaranRakshak.sustainablesathi.databinding.ActivityRedeemSikkaBinding
import com.paryavaranRakshak.sustainablesathi.seller.adapter.NearestFacilityAdapter
import com.paryavaranRakshak.sustainablesathi.seller.models.LocatorFacilityModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RedeemSikkaActivity : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivityRedeemSikkaBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRedeemSikkaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser

        //getFacility(currentUser!!.uid)

    }

    private fun getFacility(uid: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://sustainable-sathi.tech/backend/api/seller/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(InterfaceData::class.java)

        val call = retrofit.sellerCredits(uid)

        call.enqueue(object : Callback<credits> {
            override fun onResponse(
                call: Call<credits>,
                response: retrofit2.Response<credits>
            ) {
                val data = response.body()!!
                binding.tvBalance.text = data.credits.toString()

            }

            override fun onFailure(call: Call<credits>, t: Throwable) {
                println(t.message)
            }
        })
    }

}