package com.paryavaranRakshak.sustainablesathi

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.paryavaranRakshak.sustainablesathi.databinding.ActivitySellEwasteBinding

class SellEWasteActivity : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivitySellEwasteBinding

    //firebase
    private lateinit var storageRef: StorageReference

    //image uri
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellEwasteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storageRef = FirebaseStorage.getInstance().reference.child("productsImg")

        binding.cvProductImg.setOnClickListener{ resultLauncher.launch("image/*") }

        binding.btnUpload.setOnClickListener { uploadImageToFirebase("id488") }

    }

    //Image Picker
    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) {
        imageUri = it
        binding.ivProductPlaceHolder.visibility = View.GONE
        binding.ivProductImg.visibility = View.VISIBLE
        binding.ivProductImg.setImageURI(it)
    }

    private fun uploadImageToFirebase(productId: String) {
        storageRef = storageRef.child(productId)

        imageUri?.let { uri ->
            storageRef.putFile(uri)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        storageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                            imageUri = downloadUri
                            println(imageUri)
                        }
                    } else {
                        Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }


}