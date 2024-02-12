package com.paryavaranRakshak.sustainablesathi.buyer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.paryavaranRakshak.sustainablesathi.Interface.InterfaceData
import com.paryavaranRakshak.sustainablesathi.R
import com.paryavaranRakshak.sustainablesathi.buyer.adapter.CategoryProductAdapter
import com.paryavaranRakshak.sustainablesathi.buyer.adapter.ProductAdapter
import com.paryavaranRakshak.sustainablesathi.buyer.models.CategoryProductsModel
import com.paryavaranRakshak.sustainablesathi.databinding.ActivityCategoryProductsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CategoryProductsActivity : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivityCategoryProductsBinding

    //product adapter
    private lateinit var productAdapter: CategoryProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val categoryName = intent.getStringExtra("categoryName")!!

        binding.progressBar.visibility = View.VISIBLE
        setupActivity(categoryName)

    }

    private fun setupActivity(categoryName: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://sustainable-sathi.tech/backend/api/buyer/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(InterfaceData::class.java)

        val call = retrofit.getProductsByCategory(categoryName)

        call.enqueue(object : Callback<List<CategoryProductsModel>>{
            override fun onResponse(
                call: Call<List<CategoryProductsModel>>,
                response: Response<List<CategoryProductsModel>>
            ) {
                val data = response.body()!!
                productAdapter = CategoryProductAdapter(this@CategoryProductsActivity, data)
                binding.rvProducts.adapter = productAdapter
                if (productAdapter.itemCount != 0) {
                    binding.rvProducts.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    val countStr = "${productAdapter.itemCount} Products Found"
                }
            }

            override fun onFailure(call: Call<List<CategoryProductsModel>>, t: Throwable) {
                Toast.makeText(this@CategoryProductsActivity, "No product found near you!!", Toast.LENGTH_LONG).show()
            }

        })

    }
}