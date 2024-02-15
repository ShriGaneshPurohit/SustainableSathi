package com.paryavaranRakshak.sustainablesathi.seller.fragments

import android.animation.ValueAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.paryavaranRakshak.sustainablesathi.R
import com.paryavaranRakshak.sustainablesathi.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    //view binding
    private lateinit var binding: FragmentHomeBinding

    //anim
    val anim1 = ValueAnimator.ofInt(0, 15)
    val anim2 = ValueAnimator.ofInt(0, 4)
    val anim3 = ValueAnimator.ofInt(0, 68)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        startAnim()

        return binding.root
    }

    private fun startAnim() {

        //anim 1
        anim1.addUpdateListener {
          binding.tvCounter.text = "${anim1.animatedValue.toString()} Lac."
        }
        anim1.setDuration(3500)
        anim1.start()

        //anim 2
        anim3.addUpdateListener {
            binding.tvCounter2.text = "${anim2.animatedValue.toString()} Lac."
        }
        anim2.setDuration(2500)
        anim2.start()

        //anim 3
        anim3.addUpdateListener {
            binding.tvCounter3.text = "${anim3.animatedValue.toString()}%"
        }
        anim3.setDuration(2500)
        anim3.start()

    }

}