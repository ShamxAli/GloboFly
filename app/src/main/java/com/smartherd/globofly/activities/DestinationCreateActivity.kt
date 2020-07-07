package com.smartherd.globofly.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.smartherd.globofly.R
import com.smartherd.globofly.helpers.SampleData
import com.smartherd.globofly.models.Destination
import com.smartherd.globofly.services.DestinationAPI
import com.smartherd.globofly.services.RetrofitClient
import com.smartherd.globofly.utils.showToast
import kotlinx.android.synthetic.main.activity_destiny_create.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DestinationCreateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destiny_create)

        setSupportActionBar(toolbar)
        val context = this

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btn_add.setOnClickListener {
            val newDestination = Destination()
            newDestination.city = et_city.text.toString()
            newDestination.description = et_description.text.toString()
            newDestination.country = et_country.text.toString()

            // To be replaced by retrofit code

            val retrofitClient = RetrofitClient.buildService(DestinationAPI::class.java)
            val requestCall = retrofitClient.addDestination(newDestination)
            

            requestCall.enqueue(object : Callback<Destination> {

                override fun onResponse(call: Call<Destination>, response: Response<Destination>) {
                    if (response.isSuccessful) {
                        val body = response.body() // use it or ignore it
                        showToast("Created successfully")
                        finish()
                    } else {
                        showToast("Problem while creating resource")
                    }

                }

                override fun onFailure(call: Call<Destination>, t: Throwable) {
                    showToast("Server Error")
                }
            })


        }
    }
}
