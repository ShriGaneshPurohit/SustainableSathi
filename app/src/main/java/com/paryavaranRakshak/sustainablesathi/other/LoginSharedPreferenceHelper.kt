package com.paryavaranRakshak.sustainablesathi.other

import android.content.Context
import android.content.SharedPreferences

class LoginSharedPreferenceHelper(private val context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("LoginStatus", Context.MODE_PRIVATE)

    fun setLoginStatus(status: String) {
        sharedPreferences.edit().putString("Status", status).apply()
    }

    fun setUserType(userType: String) {
        sharedPreferences.edit().putString("LoginType", userType).apply()
    }

    fun setUid(uid: String) {
        sharedPreferences.edit().putString("Uid", uid).apply()
    }

    fun getLoginStatus(): String? {
        return sharedPreferences.getString("Status", "pending")
    }

    fun getUserType(): String? {
        return sharedPreferences.getString("LoginType", "none")
    }

    fun getUid(): String? {
        return sharedPreferences.getString("Uid", "none")
    }
}