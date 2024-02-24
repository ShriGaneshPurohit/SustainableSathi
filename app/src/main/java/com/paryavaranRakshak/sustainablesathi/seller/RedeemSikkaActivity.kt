package com.paryavaranRakshak.sustainablesathi.seller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.firebase.auth.FirebaseAuth
import com.paryavaranRakshak.sustainablesathi.R
import com.paryavaranRakshak.sustainablesathi.databinding.ActivityRedeemSikkaBinding

class RedeemSikkaActivity : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivityRedeemSikkaBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRedeemSikkaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser

        if (currentUser != null) {
            val profilePicUrl = currentUser.photoUrl

            // Using Glide to load the profile picture and apply the CircleCrop transformation
            Glide.with(this)
                .load(profilePicUrl)
                .transform(CircleCrop())
                //.placeholder(R.drawable.ic_guest) // Placeholder image while loading
                //.error(R.drawable.ic_guest) // Image to display in case of error
                .into(binding.ivProfile)

            binding.tvUsername.text = currentUser.displayName
        }

    }
}