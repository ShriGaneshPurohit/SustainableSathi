package com.paryavaranRakshak.sustainablesathi.Interface

import com.paryavaranRakshak.sustainablesathi.models.LoginModel
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface InterfaceData {

    //Save seller profile Api
    @FormUrlEncoded
    @POST("save-profile-api.php")
    fun saveSellerProfile(@Field("uid") uid: String,
                          @Field("name") name: String,
                          @Field("email") email: String,
                          @Field("contactNumber") contactNumber: String,
                          @Field("address") address: String,
                          @Field("city") city: String,
                          @Field("state") state: String): Call<LoginModel>

}