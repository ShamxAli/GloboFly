package com.smartherd.globofly.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.smartherd.globofly.services.DestinationAPI
import com.smartherd.globofly.R
import com.smartherd.globofly.helpers.DestinationAdapter
import com.smartherd.globofly.models.Destination
import com.smartherd.globofly.services.RetrofitClient
import com.smartherd.globofly.utils.showToast
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


    private fun loadDestinations() {

        // To be replaced by retrofit code
        //destiny_recycler_view.adapter = DestinationAdapter(SampleData.DESTINATIONS)

        val destinationService = RetrofitClient.buildService(DestinationAPI::class.java)

        val requestCall = destinationService.getDestinationList()

        /*6.3 Cancel the request*/
        // requestCall.cancel()
        // requestCall.isCanceled (if cancelled perform some action)

        // Query
        val requestCall1 =
            destinationService.getCountry("Pakistan") // Query param (if send 'null' will retrieve all)

        // Query map
        val hashMap = HashMap<String, String>()
        hashMap["country"] = "India"
        hashMap["count"] = "1"
        // req
        // destinationService.getCountryOne(hashMap)

        // enqueue performs async operations in background thread
        // Call is interface which holds all the info about http request and http response
        // object is inner class which implements Callback which has two methods...
        // Call is really important class
        // use execute instead of enqueue in case of main thread specially for splash screen
        requestCall.enqueue(object : Callback<List<Destination>> {


            // Your STATUS CODE will decide if the http response is a success or failure
            override fun onResponse(
                call: Call<List<Destination>>,
                response: Response<List<Destination>>
            ) {
                if (response.isSuccessful) {
                    val destinationList = response.body()!!
                    recyclerView.adapter = DestinationAdapter(destinationList)
                } else {
                    showToast("Response is not loaded")
                }
            }


            // does not depend on result code
            // INVOKED in case of NETWORK ERROR and establishing connection with Server
            // or ERROR creating http request or http response
            override fun onFailure(call: Call<List<Destination>>, t: Throwable) {

                showToast("Some Error Occur")
            }
        })
    }
}
