package com.smartherd.globofly.services

import com.smartherd.globofly.models.Destination
import retrofit2.Call
import retrofit2.http.*
import java.util.*
import java.util.logging.Filter
import kotlin.collections.HashMap

interface DestinationAPI {


    // Call object will be responsible for getting the list of destination from the server
    @GET("destination")
    fun getDestinationList(): Call<List<Destination>>

    // Path parameter
    @GET("destination/{id}")
    fun getDestination(@Path("id") id: Int): Call<Destination>

    // Query parameter
    @GET("destination")
    fun getCountry(@Query("country") country: String): Call<List<Destination>>

    // Query map
    @GET("destination")
    fun getCountryOne(@QueryMap filter: HashMap<String, String>): Call<List<Destination>>


    // Post request (with @Body we send data to server)
    @POST("destination")
    fun addDestination(@Body destination: Destination): Call<Destination>


    // We are sending the data in form url encoded method and getting the response in json
    // We can also use json to send data as we sent in post request
    @FormUrlEncoded
    @PUT("destination/{id}")
    fun updateDestination(
        @Path("id") id: Int,
        @Field("city") city: String,
        @Field("description") desc: String,
        @Field("country") country: String
    ): Call<Destination>


    // Delete the destination
    @DELETE("destination/{id}")
    fun deleteDestination(@Path("id") id: Int): Call<Unit>


    /* Headers */ // 5.5

    // Headers are used to send meta data to the server
    // using x because it's user defined
    @Headers("x-device-type: Android", "x-foo: bar") // static headers
    @GET("destination")
    fun getDestinationListWithHeaders(
        @Header("Accept-Language") language: String // dynamic headers
    ): Call<List<Destination>>


}