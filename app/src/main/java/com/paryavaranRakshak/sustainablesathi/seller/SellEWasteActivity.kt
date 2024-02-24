package com.paryavaranRakshak.sustainablesathi.seller

import android.app.AlertDialog
import android.app.ProgressDialog
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.paryavaranRakshak.sustainablesathi.Interface.InterfaceData
import com.paryavaranRakshak.sustainablesathi.R
import com.paryavaranRakshak.sustainablesathi.databinding.ActivitySellEwasteBinding
import com.paryavaranRakshak.sustainablesathi.other.LoginSharedPreferenceHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class SellEWasteActivity : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivitySellEwasteBinding

    //firebase
    private lateinit var storageRef: StorageReference

    //image uri
    private var imageUri: Uri? = null

    //selected category
    private var category: String? = null
    private var subCategory: String? = null

    //progress dialog
    private lateinit var pd: ProgressDialog

    //shared pref
    private lateinit var sharedPreferencesHelper: LoginSharedPreferenceHelper

    private lateinit var selectedDate: String

    private var finalPrice: Double = 0.0

    private lateinit var alertDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellEwasteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize SharedPreferencesHelper
        sharedPreferencesHelper = LoginSharedPreferenceHelper(this)

        storageRef = FirebaseStorage.getInstance().reference.child("productsImg")

        alertDialog = AlertDialog.Builder(this).create()

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

        binding.btnDatePicker.setOnClickListener {pickDate()}

        //progress dialog
        pd = ProgressDialog(this)
        pd.setMessage("Processing...")
        pd.setCancelable(false)

        binding.cvProductImg.setOnClickListener { resultLauncher.launch("image/*") }

        //binding.btnSell.setOnClickListener { uploadImageToFirebase(Calendar.getInstance().time.toString()) }

        binding.btnSell.setOnClickListener { getPrice() }

        binding.btnBack.setOnClickListener { finish() }

    }

    private fun getPrice() {
        pd.setTitle("Calculating best price...")
        pd.show()

        val sikkaPranali = sikkaPranali()

        val quantity = Integer.parseInt(binding.etQuantity.text.toString())

        val mrp: Double = (binding.etPrice.text.toString()).toDouble()

        val conditionSelected = if (binding.rbTop.isChecked){
            0
        } else if (binding.rbModerate.isChecked) {
            1
        } else if (binding.rbWorst.isChecked) {
            2
        } else if (binding.rbNotWorking.isChecked) {
            3
        } else {
            Toast.makeText(this,"Please select device condition..",Toast.LENGTH_SHORT).show()
            return
        }

        val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        finalPrice = sikkaPranali.getPrice(dateFormat.parse(selectedDate)!!,mrp,conditionSelected,quantity)
        showAlert(finalPrice.toString())

    }

    private fun pickDate() {
        val materialDateBuilder: MaterialDatePicker.Builder<Long> =
            MaterialDatePicker.Builder.datePicker()

        materialDateBuilder.setTitleText("SELECT A DATE")
        val materialDatePicker = materialDateBuilder.build()

        materialDatePicker.show(supportFragmentManager, "MATERIAL_DATE_PICKER")

        materialDatePicker.addOnPositiveButtonClickListener {
            val selectedDateInMillis = materialDatePicker.selection ?: return@addOnPositiveButtonClickListener
            val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
            selectedDate = dateFormat.format(Date(selectedDateInMillis))
        }
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
        } ?: run {
            pd.dismiss()
            Toast.makeText(this, "Image URI is null", Toast.LENGTH_SHORT).show()
        }
    }


    private fun getData(){
        pd.setTitle("Uploading product data...")

        val name = binding.etName.text.toString()
        val description = binding.etDescription.text.toString()
        val quantity = Integer.parseInt(binding.etQuantity.text.toString())
        val city = binding.etCity.text.toString()
        val state = binding.etState.text.toString()

        uploadProduct(name,description,quantity, finalPrice.toFloat(), city, state)
    }

    private fun uploadProduct(name: String, description: String, quantity: Int, price: Float, city: String, state: String) {
        // Initialize Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://sustainable-sathi.tech/backend/api/seller/")
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

    fun showAlert(price: String) {
        pd.dismiss()
        alertDialog.setTitle("Sikka Pranali")
        alertDialog.setMessage("The price for your device is - ₹ $price/-")
        alertDialog.setCancelable(false)

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Sell") { _, _ ->
            uploadImageToFirebase(Calendar.getInstance().time.toString())
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

}