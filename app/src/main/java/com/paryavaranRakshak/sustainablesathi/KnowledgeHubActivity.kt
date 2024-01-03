package com.paryavaranRakshak.sustainablesathi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.paryavaranRakshak.sustainablesathi.databinding.ActivityKnowledgeHubBinding

class KnowledgeHubActivity : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivityKnowledgeHubBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKnowledgeHubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cvStage1.setOnClickListener{
            val intent = Intent(this,YoutubePlayerActivity::class.java)
            intent.putExtra("videoId","MQLadfsvfLo")
            startActivity(intent)
        }
        binding.cvStage2.setOnClickListener{
            val intent = Intent(this,YoutubePlayerActivity::class.java)
            intent.putExtra("videoId","h5Z5m5by9UA")
            startActivity(intent)
        }
        binding.cvStage3.setOnClickListener{
            val intent = Intent(this,YoutubePlayerActivity::class.java)
            intent.putExtra("videoId","ApdkhWd7SfQ")
            startActivity(intent)
        }
        binding.cvStage4.setOnClickListener{
            val intent = Intent(this,YoutubePlayerActivity::class.java)
            intent.putExtra("videoId","-uyIzKIw0xY")
            startActivity(intent)
        }

    }

}