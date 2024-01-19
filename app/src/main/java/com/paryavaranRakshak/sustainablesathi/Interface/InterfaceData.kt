package com.paryavaranRakshak.sustainablesathi.Interface

import com.paryavaranRakshak.sustainablesathi.models.LocatorFacilityModel
import com.paryavaranRakshak.sustainablesathi.models.LoginModel
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface InterfaceData {

    //Save seller profile Api
    @FormUrlEncoded
    @POST("save-profile-api.php")
    fun saveSellerProfile(@Field("uid") uid: String,
                          @Field("name") name: String,
                          @Field("age") age: String,
                          @Field("email") email: String,
                          @Field("contactNumber") contactNumber: String,
                          @Field("address") address: String,
                          @Field("city") city: String,
                          @Field("state") state: String): Call<LoginModel>

    //Save buyer profile Api
    @FormUrlEncoded
    @POST("save-profile-api.php")
    fun saveBuyerProfile(@Field("uid") uid: String,
                          @Field("name") name: String,
                          @Field("gstn") gstn: String,
                          @Field("email") email: String,
                          @Field("contactNumber") contactNumber: String,
                          @Field("address") address: String,
                          @Field("city") city: String,
                          @Field("state") state: String): Call<LoginModel>

    //sell e-waste
    @FormUrlEncoded
    @POST("sell-e-waste-api.php") // Replace with your actual API endpoint
    fun uploadProduct(
        @Field("name") name: String,
        @Field("imageLink") imageLink: String,
        @Field("category") category: String,
        @Field("description") description: String,
        @Field("quantity") quantity: Int,
        @Field("price") price: Int,
        @Field("sellerUID") sellerUID: String,
        @Field("city") city: String,
        @Field("state") state: String
    ): Call<Void>

    //Get facility Api
    @GET("facility-locator-api.php")
    fun getFacility(@Query("uid") uid: String): Call<List<LocatorFacilityModel>>


}