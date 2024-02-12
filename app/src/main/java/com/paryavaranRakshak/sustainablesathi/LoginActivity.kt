package com.paryavaranRakshak.sustainablesathi

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.paryavaranRakshak.sustainablesathi.Interface.InterfaceData
import com.paryavaranRakshak.sustainablesathi.buyer.BuyerDashboardActivity
import com.paryavaranRakshak.sustainablesathi.databinding.ActivityLoginBinding
import com.paryavaranRakshak.sustainablesathi.models.LoginModel
import com.paryavaranRakshak.sustainablesathi.other.LoginSharedPreferenceHelper
import com.paryavaranRakshak.sustainablesathi.seller.SellerDashboardActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class LoginActivity : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivityLoginBinding

    //login
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    //shared pref
    private lateinit var sharedPreferencesHelper: LoginSharedPreferenceHelper

    //progress dialog
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize SharedPreferencesHelper
        sharedPreferencesHelper = LoginSharedPreferenceHelper(this)

        //login
        auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        //initialize progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait...")
        progressDialog.setCancelable(false)

        //btn google
        binding.btnGoogle.setOnClickListener {
            val currentUser = auth.currentUser
            if (currentUser != null) {
                // User is already signed in, navigate to the main activity
                checkProfile()
            } else {
                // User is not signed in, proceed with Google sign-in
                signInGoogle()
            }
        }

    }

    private fun signInGoogle() {
        progressDialog.show()
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleResults(task)
            }
        }

    // Inside handleResults function
    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            val account: GoogleSignInAccount? = task.result
            account?.let { updateUI(it) }
        } else {
            Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Save onboarding status and login type in SharedPreferences
                checkProfile()
            } else {
                Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkProfile() {
        val currentUser = auth.currentUser
        val retrofit = Retrofit.Builder()
            .baseUrl("https://sustainable-sathi.tech/backend/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(InterfaceData::class.java)

        val call = retrofit.checkProfile(currentUser!!.uid, currentUser.displayName!!)

        call.enqueue(object : Callback<LoginModel> {
            override fun onResponse(
                call: Call<LoginModel>,
                response: Response<LoginModel>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()!!
                    if (data.message.contains("Buyer")) {
                        // Save status and user type in SharedPreferences
                        sharedPreferencesHelper.setLoginStatus("completed")
                        sharedPreferencesHelper.setUserType("buyer")
                        sharedPreferencesHelper.setUid(currentUser.uid)
                        progressDialog.dismiss()
                        startActivity(Intent(this@LoginActivity, BuyerDashboardActivity::class.java))
                        finish()
                    } else if (data.message.contains("Seller")) {
                        // Save status and user type in SharedPreferences
                        sharedPreferencesHelper.setLoginStatus("completed")
                        sharedPreferencesHelper.setUserType("seller")
                        sharedPreferencesHelper.setUid(currentUser.uid)
                        progressDialog.dismiss()
                        startActivity(Intent(this@LoginActivity, SellerDashboardActivity::class.java))
                        finish()
                    } else {
                        println(data.message)
                        exit()
                    }
                }
            }

            override fun onFailure(call: Call<LoginModel>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Error checking profile!!!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun exit() {
        progressDialog.dismiss()
        val intent: Intent = Intent(this, ProfileRegistrationActivity::class.java)
        startActivity(intent)
        finish()
    }

}