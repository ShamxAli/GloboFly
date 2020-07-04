package com.smartherd.globofly.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.smartherd.globofly.services.DestinationAPI
import com.smartherd.globofly.R
import com.smartherd.globofly.helpers.DestinationAdapter
import com.smartherd.globofly.models.Destination
import com.smartherd.globofly.services.Client
import kotlinx.android.synthetic.main.activity_destiny_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DestinationListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destiny_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        fab.setOnClickListener {
            val intent = Intent(this@DestinationListActivity, DestinationCreateActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        loadDestinations()
    }

    private fun loadDestinationsss() {

        // To be replaced by retrofit code
        //destiny_recycler_view.adapter = DestinationAdapter(SampleData.DESTINATIONS)

        val buildService = Client.buildService(DestinationAPI::class.java)

        val requestCall = buildService.getDestinationList()


        // enqueue performs async operations in background thread
        // Call is interface which holds all the info about http request and http response
        // object is inner class which implements Callback which has two methods...
        // Call is really important class
        requestCall.enqueue(object : Callback<List<Destination>> {

            override fun onResponse(
                call: Call<List<Destination>>,
                response: Response<List<Destination>>
            ) {
                if (response.isSuccessful) {
                    // response.body() will return the list of destinations
                    val destinationList = response.body()!!

                    val adapter = DestinationAdapter(destinationList)
                    recyclerView.adapter = adapter // == recyclerView.setAdapter = adapter

                }
            }

            override fun onFailure(call: Call<List<Destination>>, t: Throwable) {

            }


        })


    }

    private fun loadDestinations() {


        // To be replaced by retrofit code
        //destiny_recycler_view.adapter = DestinationAdapter(SampleData.DESTINATIONS)

        val destinationService = Client.buildService(DestinationAPI::class.java)

        val requestCall = destinationService.getDestinationList()


        // enqueue performs async operations in background thread
        // Call is interface which holds all the info about http request and http response
        // object is inner class which implements Callback which has two methods...
        // Call is really important class
        requestCall.enqueue(object : Callback<List<Destination>> {

            override fun onResponse(
                call: Call<List<Destination>>,
                response: Response<List<Destination>>
            ) {
                if (response.isSuccessful) {
                    val destinationList = response.body()!!
                    recyclerView.adapter = DestinationAdapter(destinationList)
                }
            }

            override fun onFailure(call: Call<List<Destination>>, t: Throwable) {


            }
        })
    }
}
