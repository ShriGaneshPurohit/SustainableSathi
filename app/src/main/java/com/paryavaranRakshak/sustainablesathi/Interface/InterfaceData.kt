package com.paryavaranRakshak.sustainablesathi.Interface

import com.paryavaranRakshak.sustainablesathi.models.LocatorFacilityModel
import com.paryavaranRakshak.sustainablesathi.models.LoginModel
import com.paryavaranRakshak.sustainablesathi.models.ProductsModel
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface InterfaceData {

    //Save seller profile Api
    @FormUrlEncoded
    @POST("save-profile.php")
    fun saveSellerProfile(
        @Field("uid") uid: String,
        @Field("name") name: String,
        @Field("age") age: String,
        @Field("email") email: String,
        @Field("contactNumber") contactNumber: String,
        @Field("address") address: String,
        @Field("city") city: String,
        @Field("state") state: String,
        @Field("latitude") latitude: String,
        @Field("longitude") longitude: String
    ): Call<LoginModel>

    //Save buyer profile Api
    @FormUrlEncoded
    @POST("save-profile.php")
    fun saveBuyerProfile(
        @Field("uid") uid: String,
        @Field("name") name: String,
        @Field("gstn") gstn: String,
        @Field("email") email: String,
        @Field("contactNumber") contactNumber: String,
        @Field("address") address: String,
        @Field("city") city: String,
        @Field("state") state: String,
        @Field("latitude") latitude: String,
        @Field("longitude") longitude: String
    ): Call<LoginModel>

    //sell e-waste
    @FormUrlEncoded
    @POST("sell-product.php") // Replace with your actual API endpoint
    fun uploadProduct(
        @Field("name") name: String,
        @Field("imageLink") imageLink: String,
        @Field("category") category: String,
        @Field("subCategory") subCategory: String,
        @Field("description") description: String,
        @Field("quantity") quantity: Int,
        @Field("price") price: Float,
        @Field("sellerUID") sellerUID: String,
        @Field("city") city: String,
        @Field("state") state: String
    ): Call<Void>

    //Get facility Api
    @GET("facility-locator.php")
    fun getFacility(@Query("uid") uid: String): Call<List<LocatorFacilityModel>>

    //Get Near you product Api
    @GET("near-you-product.php")
    fun getNearByProducts(@Query("uid") uid: String): Call<List<ProductsModel>>

}