package com.paryavaranRakshak.sustainablesathi.buyer

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.paryavaranRakshak.sustainablesathi.databinding.ActivityProductViewBinding
import com.paryavaranRakshak.sustainablesathi.buyer.utils.ProductUtils

class ProductViewActivity : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivityProductViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initializing data
        initialize()

        binding.btnBack.setOnClickListener{finish()}

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

}