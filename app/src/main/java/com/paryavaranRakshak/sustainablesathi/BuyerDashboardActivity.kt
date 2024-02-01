package com.paryavaranRakshak.sustainablesathi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.paryavaranRakshak.sustainablesathi.databinding.ActivityBuyerDashboardBinding

class BuyerDashboardActivity : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivityBuyerDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuyerDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ViewPagerAdapter(this)
        binding.viewpager2.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewpager2) { tab, position ->
            tab.text = when (position) {
                0 -> "Near You"
                1 -> "Categories"
                else -> throw IllegalArgumentException("Invalid position")
            }
        }.attach()

    }
}

class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2 // Number of fragments
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> NearYouFragment()
            1 -> CategoriesFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}