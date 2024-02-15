package com.paryavaranRakshak.sustainablesathi.seller.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.paryavaranRakshak.sustainablesathi.R
import com.paryavaranRakshak.sustainablesathi.databinding.FragmentGyaanKoshBinding
import com.paryavaranRakshak.sustainablesathi.seller.YoutubePlayerActivity

class GyaanKoshFragment : Fragment() {

    //view binding
    private lateinit var binding: FragmentGyaanKoshBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGyaanKoshBinding.inflate(layoutInflater)

        binding.cvStage1.setOnClickListener {
            val intent = Intent(requireContext(), YoutubePlayerActivity::class.java)
            intent.putExtra("videoId", "MQLadfsvfLo")
            startActivity(intent)
        }
        binding.cvStage2.setOnClickListener {
            val intent = Intent(requireContext(), YoutubePlayerActivity::class.java)
            intent.putExtra("videoId", "h5Z5m5by9UA")
            startActivity(intent)
        }
        binding.cvStage3.setOnClickListener {
            val intent = Intent(requireContext(), YoutubePlayerActivity::class.java)
            intent.putExtra("videoId", "ApdkhWd7SfQ")
            startActivity(intent)
        }
        binding.cvStage4.setOnClickListener {
            val intent = Intent(requireContext(), YoutubePlayerActivity::class.java)
            intent.putExtra("videoId", "-uyIzKIw0xY")
            startActivity(intent)
        }

        return binding.root
    }

}