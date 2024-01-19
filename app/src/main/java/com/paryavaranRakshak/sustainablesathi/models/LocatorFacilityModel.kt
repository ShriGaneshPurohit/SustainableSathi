package com.paryavaranRakshak.sustainablesathi.models

import com.google.gson.annotations.SerializedName

data class LocatorFacilityModel(
    val uid: String,
    val name: String,
    val contactNumber: String,
    val latitude: String,
    val longitude: String,
    val distance: Double
)