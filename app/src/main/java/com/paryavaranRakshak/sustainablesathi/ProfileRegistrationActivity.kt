package com.paryavaranRakshak.sustainablesathi

import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.firebase.auth.FirebaseAuth
import com.paryavaranRakshak.sustainablesathi.Interface.InterfaceData
import com.paryavaranRakshak.sustainablesathi.buyer.BuyerDashboardActivity
import com.paryavaranRakshak.sustainablesathi.databinding.ActivityProfileRegistrationBinding
import com.paryavaranRakshak.sustainablesathi.models.LoginModel
import com.paryavaranRakshak.sustainablesathi.other.LoginSharedPreferenceHelper
import com.paryavaranRakshak.sustainablesathi.seller.SellerDashboardActivity
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

    private val LOCATION_PERMISSION_REQUEST_CODE = 1

    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

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
        binding.autoCompleteTextView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                accountSelected = position
                if (position == 0) {
                    binding.age.visibility = View.GONE
                    binding.gstn.visibility = View.VISIBLE
                } else {
                    binding.age.visibility = View.VISIBLE
                    binding.gstn.visibility = View.GONE
                }
            }

        //btnSave
        binding.btnSave.setOnClickListener {
            getData(
                currentUser!!.uid,
                currentUser.displayName!!,
                currentUser.email!!
            )
        }

    }

    //Getting Data from edit texts
    private fun getData(uid: String, name: String, email: String) {
        pd.show()
        getCoordinates()
        if (latitude != 0.0 && longitude != 0.0) {
            val contactNumber = binding.etContact.text.toString()
            val address = binding.etAddress.text.toString()
            val city = binding.etCity.text.toString()
            val state = binding.etState.text.toString()

            when (accountSelected) {
                0 -> {
                    val gstn = binding.etGstn.text.toString()
                    registerBuyer(uid, name, email, gstn, contactNumber, address, city, state)
                }

                1 -> {
                    val age = binding.etAge.text.toString()
                    registerSeller(uid, name, email, age, contactNumber, address, city, state)
                }

                else -> {
                    Toast.makeText(this, "Please select account type..", Toast.LENGTH_SHORT).show()
                    pd.dismiss()
                }
            }
        } else {
            Toast.makeText(this,"Location Required...",Toast.LENGTH_SHORT).show()
            pd.dismiss()
        }
    }

    private fun registerBuyer(
        uid: String,
        name: String,
        email: String,
        gstn: String,
        contactNumber: String,
        address: String,
        city: String,
        state: String
    ) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://sustainable-sathi.tech/backend/api/buyer/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(InterfaceData::class.java)

        val call =
            service.saveBuyerProfile(uid, name, gstn, email, contactNumber, address, city, state, latitude.toString(), longitude.toString())

        call.enqueue(object : Callback<LoginModel> {
            override fun onResponse(call: Call<LoginModel>, response: Response<LoginModel>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@ProfileRegistrationActivity,
                        "$name welcome 👋👋!!",
                        Toast.LENGTH_SHORT
                    ).show()
                    // Save status and user type in SharedPreferences
                    sharedPreferencesHelper.setLoginStatus("completed")
                    sharedPreferencesHelper.setUserType("buyer")
                    sharedPreferencesHelper.setUid(uid)
                    pd.dismiss()
                    startActivity(
                        Intent(
                            this@ProfileRegistrationActivity,
                            BuyerDashboardActivity::class.java
                        )
                    )
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
    private fun registerSeller(
        uid: String,
        name: String,
        email: String,
        age: String,
        contactNumber: String,
        address: String,
        city: String,
        state: String
    ) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://sustainable-sathi.tech/backend/api/seller/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(InterfaceData::class.java)

        val call =
            retrofit.saveSellerProfile(uid, name, age, email, contactNumber, address, city, state, latitude.toString(), longitude.toString())

        call.enqueue(object : Callback<LoginModel> {
            override fun onResponse(
                call: Call<LoginModel>,
                response: retrofit2.Response<LoginModel>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@ProfileRegistrationActivity,
                        "$name welcome 👋👋!!",
                        Toast.LENGTH_SHORT
                    ).show()
                    // Save status and user type in SharedPreferences
                    sharedPreferencesHelper.setLoginStatus("completed")
                    sharedPreferencesHelper.setUserType("seller")
                    sharedPreferencesHelper.setUid(uid)
                    pd.dismiss()
                    startActivity(
                        Intent(
                            this@ProfileRegistrationActivity,
                            SellerDashboardActivity::class.java
                        )
                    )
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
        Toast.makeText(
            this@ProfileRegistrationActivity,
            "Something went wrong..",
            Toast.LENGTH_SHORT
        ).show()
        pd.dismiss()
    }

    private fun getCoordinates() {
        if (isLocationPermissionGranted()) {
            getLastLocation()
        } else {
            requestLocationPermission()
        }
    }

    private fun isLocationPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    private fun getLastLocation() {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        val providers: List<String> = locationManager.getProviders(true)
        var bestLocation: Location? = null

        for (provider in providers) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val location = locationManager.getLastKnownLocation(provider)
                location?.let {
                    if (bestLocation == null || it.accuracy < bestLocation!!.accuracy) {
                        bestLocation = it
                    }
                }
            }
        }

        bestLocation?.let {
            latitude = it.latitude
            longitude = it.longitude
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation()
            } else {
                // Handle the case when the user denies the location permission
                // You might want to show a message or take appropriate action
                // For example, show a SnackBar or AlertDialog to inform the user
            }
        }
    }

}