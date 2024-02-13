package com.paryavaranRakshak.sustainablesathi.buyer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.paryavaranRakshak.sustainablesathi.R
import com.paryavaranRakshak.sustainablesathi.databinding.FragmentCatagoriesBinding

class CategoriesFragment : Fragment() {

    //view binding
    private lateinit var binding: FragmentCatagoriesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCatagoriesBinding.inflate(layoutInflater)

        binding.cvAc.setOnClickListener{ getCategory("AC") }

        binding.cvMobile.setOnClickListener { getCategory("Mobile") }

        return binding.root
    }

    private fun getCategory(categoryName: String) {
        val intent = Intent(context,CategoryProductsActivity::class.java)
        intent.putExtra("categoryName", categoryName)
        startActivity(intent)
    }

}