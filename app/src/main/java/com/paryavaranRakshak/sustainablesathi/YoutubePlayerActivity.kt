package com.paryavaranRakshak.sustainablesathi

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.paryavaranRakshak.sustainablesathi.databinding.ActivityYoutubePlayerBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo

class YoutubePlayerActivity : AppCompatActivity() {

    //view binding
    private lateinit var binding:  ActivityYoutubePlayerBinding

    private lateinit var youTubePlayer: YouTubePlayer
    private var isFullScreen = false

    private var videoId: String? = null

    private val onBackPressedCallback = object : OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            if (isFullScreen){
                youTubePlayer.toggleFullscreen()
            }else{
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYoutubePlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //back button
        onBackPressedDispatcher.addCallback(onBackPressedCallback)

        lifecycle.addObserver(binding.ytPlayer)

        binding.ytPlayer.addFullscreenListener(object : FullscreenListener{
            override fun onEnterFullscreen(fullscreenView: View, exitFullscreen: () -> Unit) {
                isFullScreen = true
                binding.container.visibility = View.VISIBLE
                binding.container.addView(fullscreenView)

                WindowInsetsControllerCompat(window!!,binding.rootView).apply {
                    hide(WindowInsetsCompat.Type.systemBars())
                    systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                }

                if (requestedOrientation != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                }

            }

            override fun onExitFullscreen() {
                isFullScreen = false
                binding.container.visibility = View.GONE
                binding.container.removeAllViews()

                WindowInsetsControllerCompat(window!!,binding.rootView).apply {
                    show(WindowInsetsCompat.Type.systemBars())
                }

                if (requestedOrientation != ActivityInfo.SCREEN_ORIENTATION_SENSOR){
                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
                }

            }

        })

        val youtubePlayerListener = object : AbstractYouTubePlayerListener(){
            override fun onReady(youTubePlayer: YouTubePlayer) {
                this@YoutubePlayerActivity.youTubePlayer = youTubePlayer
                youTubePlayer.loadOrCueVideo(lifecycle,videoId!!,0f)
            }
        }

        val  iFramePlayerOptions = IFramePlayerOptions.Builder()
            .controls(1)
            .fullscreen(1)
            .autoplay(1)
            .build()

        binding.ytPlayer.enableAutomaticInitialization = false
        binding.ytPlayer.initialize(youtubePlayerListener,iFramePlayerOptions)
        youTubePlayer.toggleFullscreen()

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            if (!isFullScreen){
                youTubePlayer.toggleFullscreen()
            }
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (!isFullScreen){
                youTubePlayer.toggleFullscreen()
            }
        }
    }

}