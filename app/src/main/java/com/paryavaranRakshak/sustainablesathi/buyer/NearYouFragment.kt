package com.paryavaranRakshak.sustainablesathi.buyer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.paryavaranRakshak.sustainablesathi.Interface.InterfaceData
import com.paryavaranRakshak.sustainablesathi.buyer.adapter.ProductAdapter
import com.paryavaranRakshak.sustainablesathi.databinding.FragmentNearYouBinding
import com.paryavaranRakshak.sustainablesathi.buyer.models.ProductsModel
import com.paryavaranRakshak.sustainablesathi.other.LoginSharedPreferenceHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NearYouFragment : Fragment() {

    //view binding
    private lateinit var binding: FragmentNearYouBinding

    //shared pref
    private lateinit var sharedPreferencesHelper: LoginSharedPreferenceHelper

    //product adapter
    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNearYouBinding.inflate(layoutInflater)

        binding.rvProduct.visibility = View.INVISIBLE
        binding.progressBar.visibility = View.VISIBLE

        // Initialize SharedPreferencesHelper
        sharedPreferencesHelper = LoginSharedPreferenceHelper(requireContext())

        binding.rvProduct.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        getProduct()

        return binding.root
    }

    private fun getProduct() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://sustainable-sathi.tech/backend/api/buyer/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(InterfaceData::class.java)

        val call = retrofit.getNearByProducts(sharedPreferencesHelper.getUid()!!)

        call.enqueue(object : Callback<List<ProductsModel>> {
            override fun onResponse(
                call: Call<List<ProductsModel>>,
                response: Response<List<ProductsModel>>
            ) {
                val data = response.body()!!
                productAdapter = ProductAdapter(requireContext(), data)
                binding.rvProduct.adapter = productAdapter
                if (productAdapter.itemCount != 0) {
                    binding.rvProduct.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    val countStr = "${productAdapter.itemCount} Products Found"
                    binding.tvProductCount.text = countStr
                }
            }

            override fun onFailure(call: Call<List<ProductsModel>>, t: Throwable) {
                Toast.makeText(context, "No product found near you!!", Toast.LENGTH_LONG).show()
                println(t.message)
            }
        })

    }

}