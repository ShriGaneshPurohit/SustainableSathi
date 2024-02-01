package com.paryavaranRakshak.sustainablesathi

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.firebase.auth.FirebaseAuth
import com.paryavaranRakshak.sustainablesathi.Interface.InterfaceData
import com.paryavaranRakshak.sustainablesathi.databinding.ActivityProfileRegistrationBinding
import com.paryavaranRakshak.sustainablesathi.models.LoginModel
import com.paryavaranRakshak.sustainablesathi.other.LoginSharedPreferenceHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProfileRegistrationActivity : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivityProfileRegistrationBinding

    //User auth data
    private lateinit var auth: FirebaseAuth

    //Account selected 0->buyer, 1->seller, 2->not selected yet
    private var accountSelected: Int = 2

    //progress dialog
    private lateinit var pd: ProgressDialog

    //shared pref
    private lateinit var sharedPreferencesHelper: LoginSharedPreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize SharedPreferencesHelper
        sharedPreferencesHelper = LoginSharedPreferenceHelper(this)

        //user auth data
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        //getting google profile details
        if (currentUser != null) {
            val profilePicUrl = currentUser.photoUrl

            // Using Glide to load the profile picture and apply the CircleCrop transformation
            Glide.with(this)
                .load(profilePicUrl)
                .transform(CircleCrop())
                //.placeholder(R.drawable.ic_guest) // Placeholder image while loading
                //.error(R.drawable.ic_guest) // Image to display in case of error
                .into(binding.ivProfile)

            val welcome = currentUser.displayName + ", welcome 👋👋!!"
            binding.tvUsername.text = welcome
            binding.tvEmail.text = currentUser.email

        }

        //progress dialog
        pd = ProgressDialog(this)
        pd.setMessage("Processing...")
        pd.setCancelable(false)

        //Setting up account type
        val accountsTypes = resources.getStringArray(R.array.account_types)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, accountsTypes)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)

        //autoCompleteTextView selection listener
        binding.autoCompleteTextView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            accountSelected = position
            if (position == 0){
                binding.age.visibility = View.GONE
                binding.gstn.visibility = View.VISIBLE
            } else {
                binding.age.visibility = View.VISIBLE
                binding.gstn.visibility = View.GONE
            }
        }

        //btnSave
        binding.btnSave.setOnClickListener { getData(currentUser!!.uid, currentUser.displayName!!, currentUser.email!!) }

    }

    //Getting Data from edit texts
    private fun getData(uid: String, name: String, email: String) {
        pd.show()
        val contactNumber = binding.etContact.text.toString()
        val address = binding.etAddress.text.toString()
        val city = binding.etCity.text.toString()
        val state = binding.etState.text.toString()

        when (accountSelected) {
            0 -> {
                val gstn = binding.etGstn.text.toString()
                registerBuyer(uid,name,email,gstn,contactNumber,address,city,state)
            }
            1 -> {
                val age = binding.etAge.text.toString()
                registerSeller(uid,name,email,age,contactNumber,address,city,state)
            }
            else -> {
                Toast.makeText(this,"Please select account type..",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registerBuyer(uid: String, name: String, email: String, gstn: String, contactNumber: String, address: String, city: String, state: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://sustainable-sathi.000webhostapp.com/buyer/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(InterfaceData::class.java)

        val call = service.saveBuyerProfile(uid, name, gstn, email, contactNumber, address, city, state)

        call.enqueue(object : Callback<LoginModel> {
            override fun onResponse(call: Call<LoginModel>, response: Response<LoginModel>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@ProfileRegistrationActivity, "$name welcome 👋👋!!", Toast.LENGTH_SHORT).show()
                    // Save status and user type in SharedPreferences
                    sharedPreferencesHelper.setLoginStatus("completed")
                    sharedPreferencesHelper.setUserType("buyer")
                    sharedPreferencesHelper.setUid(uid)
                    pd.dismiss()
                    startActivity(Intent(this@ProfileRegistrationActivity, BuyerDashboardActivity::class.java))
                } else {
                    // Handle unsuccessful response
                    handleError()
                }
            }

            override fun onFailure(call: Call<LoginModel>, t: Throwable) {
                // Handle network failure
                handleError()
            }
        })
    }

    //Register Seller
    private fun registerSeller(uid: String, name: String, email: String, age: String, contactNumber: String, address: String, city: String, state: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://sustainable-sathi.000webhostapp.com/seller/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(InterfaceData::class.java)

        val call = retrofit.saveSellerProfile(uid,name,age,email,contactNumber,address,city,state)

        call.enqueue(object : Callback<LoginModel> {
            override fun onResponse(call: Call<LoginModel>, response: retrofit2.Response<LoginModel>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@ProfileRegistrationActivity,"$name welcome 👋👋!!",Toast.LENGTH_SHORT).show()
                    // Save status and user type in SharedPreferences
                    sharedPreferencesHelper.setLoginStatus("completed")
                    sharedPreferencesHelper.setUserType("seller")
                    sharedPreferencesHelper.setUid(uid)
                    pd.dismiss()
                    startActivity(Intent(this@ProfileRegistrationActivity,SellerDashboardActivity::class.java))
                } else {
                    handleError()
                }
            }

            override fun onFailure(call: Call<LoginModel>, t: Throwable) {
               handleError()
            }
        })
    }

    private fun handleError() {
        Toast.makeText(this@ProfileRegistrationActivity, "Something went wrong..", Toast.LENGTH_SHORT).show()
        pd.dismiss()
    }

}