package com.paryavaranRakshak.sustainablesathi.Interface

import com.paryavaranRakshak.sustainablesathi.buyer.models.CategoryProductsModel
import com.paryavaranRakshak.sustainablesathi.seller.models.LocatorFacilityModel
import com.paryavaranRakshak.sustainablesathi.models.LoginModel
import com.paryavaranRakshak.sustainablesathi.buyer.models.ProductsModel
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
    @GET("save-profile.php")
    fun saveBuyerProfile(
        @Query("uid") uid: String,
        @Query("name") name: String,
        @Query("gstn") gstn: String,
        @Query("email") email: String,
        @Query("contactNumber") contactNumber: String,
        @Query("address") address: String,
        @Query("city") city: String,
        @Query("state") state: String,
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String
    ): Call<LoginModel>

    //check login
    @GET("checkRecord.php")
    fun checkProfile(
        @Query("uid") uid: String,
        @Query("name") name: String
    ): Call<LoginModel>


    //sell e-waste
    @GET("sell-product.php") // Replace with your actual API endpoint
    fun uploadProduct(
        @Query("name") name: String,
        @Query("imageLink") imageLink: String,
        @Query("category") category: String,
        @Query("description") description: String,
        @Query("quantity") quantity: Int,
        @Query("price") price: Float,
        @Query("sellerUID") sellerUID: String,
        @Query("city") city: String,
        @Query("state") state: String
    ): Call<Void>

    //Get facility Api
    @GET("facility-locator.php")
    fun getFacility(@Query("uid") uid: String): Call<List<LocatorFacilityModel>>

    //Get Near you product Api
    @GET("near-you-product.php")
    fun getNearByProducts(@Query("uid") uid: String): Call<List<ProductsModel>>

    //Get category product Api
    @GET("product-category-api.php")
    fun getProductsByCategory(@Query("category") category: String): Call<List<CategoryProductsModel>>

}