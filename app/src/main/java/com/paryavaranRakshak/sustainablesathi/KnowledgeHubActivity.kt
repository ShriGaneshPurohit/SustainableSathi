package com.paryavaranRakshak.sustainablesathi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.paryavaranRakshak.sustainablesathi.databinding.ActivityKnowledgeHubBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener

class KnowledgeHubActivity : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivityKnowledgeHubBinding

    //yt player
    private lateinit var youTubePlayer: YouTubePlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKnowledgeHubBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}