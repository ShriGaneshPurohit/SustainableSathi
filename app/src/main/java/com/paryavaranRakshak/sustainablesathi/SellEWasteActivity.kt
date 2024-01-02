package com.paryavaranRakshak.sustainablesathi

import android.app.ProgressDialog
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.paryavaranRakshak.sustainablesathi.Interface.InterfaceData
import com.paryavaranRakshak.sustainablesathi.databinding.ActivitySellEwasteBinding
import com.paryavaranRakshak.sustainablesathi.other.LoginSharedPreferenceHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Calendar

class SellEWasteActivity : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivitySellEwasteBinding

    //firebase
    private lateinit var storageRef: StorageReference

    //image uri
    private var imageUri: Uri? = null

    //selected category
    private var category: String? = null

    //progress dialog
    private lateinit var pd: ProgressDialog

    //shared pref
    private lateinit var sharedPreferencesHelper: LoginSharedPreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellEwasteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize SharedPreferencesHelper
        sharedPreferencesHelper = LoginSharedPreferenceHelper(this)

        storageRef = FirebaseStorage.getInstance().reference.child("productsImg")

        // Setting up categories
        val categories = resources.getStringArray(R.array.eWaste_category)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, categories)
        binding.etCategory.adapter = arrayAdapter

        // Spinner item selection listener
        binding.etCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                category = categories[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle the case where nothing is selected, if needed
            }
        }


        //progress dialog
        pd = ProgressDialog(this)
        pd.setMessage("Processing...")
        pd.setCancelable(false)

        binding.cvProductImg.setOnClickListener { resultLauncher.launch("image/*") }

        binding.btnSell.setOnClickListener { uploadImageToFirebase(Calendar.getInstance().time.toString()) }

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
        pd.show()
        pd.setTitle("Uploading product image...")

        storageRef = storageRef.child(productId)

        imageUri?.let { uri ->
            storageRef.putFile(uri)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        storageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                            imageUri = downloadUri
                            getData()
                        }
                    } else {
                        pd.dismiss()
                        Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun getData(){
        pd.setTitle("Uploading product data...")

        val name = binding.etName.text.toString()
        val description = binding.etDescription.text.toString()
        val quantity = Integer.parseInt(binding.etQuantity.text.toString())
        val price = Integer.parseInt(binding.etPrice.text.toString())
        val city = binding.etCity.text.toString()
        val state = binding.etState.text.toString()

        uploadProduct(name,description,quantity, price, city, state)
    }

    private fun uploadProduct(name: String, description: String, quantity: Int, price: Int, city: String, state: String) {
        // Initialize Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://sustainable-sathi.000webhostapp.com/seller/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(InterfaceData::class.java)

        val call = service.uploadProduct(name, imageUri.toString(), category!!, description, quantity, price, sharedPreferencesHelper.getUid()!!, city, state)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@SellEWasteActivity,"Your e-waste is listed on the app...", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@SellEWasteActivity,"Something went wrong...", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@SellEWasteActivity,"Something went wrong...", Toast.LENGTH_SHORT).show()
            }
        })
        pd.dismiss()
    }

}