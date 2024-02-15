package com.paryavaranRakshak.sustainablesathi.buyer

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.paryavaranRakshak.sustainablesathi.Interface.InterfaceData
import com.paryavaranRakshak.sustainablesathi.buyer.adapter.ProductAdapter
import com.paryavaranRakshak.sustainablesathi.buyer.models.BuyProductModel
import com.paryavaranRakshak.sustainablesathi.buyer.models.ProductsModel
import com.paryavaranRakshak.sustainablesathi.databinding.ActivityProductViewBinding
import com.paryavaranRakshak.sustainablesathi.buyer.utils.ProductUtils
import com.paryavaranRakshak.sustainablesathi.other.LoginSharedPreferenceHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductViewActivity : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivityProductViewBinding

    //shared pref
    private lateinit var sharedPreferencesHelper: LoginSharedPreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferencesHelper = LoginSharedPreferenceHelper(this)

        //initializing data
        initialize()

        binding.btnBack.setOnClickListener{ finish() }

        binding.btnBuy.setOnClickListener { buyProduct() }

    }

    private fun initialize(){
        binding.progressBar.visibility = View.VISIBLE

        binding.tvTitle.text = ProductUtils.productName
        binding.tvProductName.text = ProductUtils.productName
        binding.tvProductDesc.text = ProductUtils.description
        binding.btnBuy.text = ProductUtils.price.toString()

        Glide.with(this)
            .load(ProductUtils.imageLink)
            .into(binding.ivProduct)

        binding.progressBar.visibility = View.GONE

    }

    private fun buyProduct(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://sustainable-sathi.tech/backend/api/buyer/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(InterfaceData::class.java)

        val call = apiService.buyProduct(sharedPreferencesHelper.getUid()!!, ProductUtils.productId.toString())

        call.enqueue(object : Callback<BuyProductModel> {
            override fun onResponse(call: Call<BuyProductModel>, response: Response<BuyProductModel>) {
                if (response.isSuccessful) {
                    val resultMessage = response.body()?.message
                    Toast.makeText(this@ProductViewActivity, resultMessage, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@ProductViewActivity, "API request failed", Toast.LENGTH_SHORT).show()
                    // Log the error for further investigation
                    Log.e("API_CALL", "Unsuccessful response: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<BuyProductModel>, t: Throwable) {
                Toast.makeText(this@ProductViewActivity, "No product found near you!!", Toast.LENGTH_LONG).show()
                // Log the error for further investigation
                Log.e("API_CALL", "Request failed: ${t.message}")
            }
        })

    }

}