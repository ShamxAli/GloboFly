package com.smartherd.globofly.services

import com.smartherd.globofly.models.Destination
import retrofit2.Call
import retrofit2.http.GET

interface DestinationAPI {


    // Call object will be responsible for getting the list of destination from the server
    @GET("destination")
    fun getDestinationList(): Call<List<Destination>>


}