package com.paryavaranRakshak.sustainablesathi

import android.animation.ValueAnimator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.paryavaranRakshak.sustainablesathi.databinding.ActivityTestingBinding

class TestingActivity : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivityTestingBinding

    val anim = ValueAnimator.ofInt(0, 68)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestingBinding.inflate(layoutInflater)
        setContentView(binding.root)


        anim.addUpdateListener {
            binding.tvCounter.text = anim.animatedValue.toString()
        }

        anim.setDuration(2500);
        anim.start();

    }
}