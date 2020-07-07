package com.smartherd.globofly.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.smartherd.globofly.R
import com.smartherd.globofly.helpers.SampleData
import com.smartherd.globofly.models.Destination
import com.smartherd.globofly.services.DestinationAPI
import com.smartherd.globofly.services.RetrofitClient
import com.smartherd.globofly.utils.showToast
import kotlinx.android.synthetic.main.activity_destiny_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class DestinationDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destiny_detail)

        setSupportActionBar(detail_toolbar)
        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val bundle: Bundle? = intent.extras

        if (bundle?.containsKey(ARG_ITEM_ID)!!) {

            val id = intent.getIntExtra(ARG_ITEM_ID, 0)

            loadDetails(id)

            initUpdateButton(id)

            initDeleteButton(id)
        }
    }

    private fun loadDetails(id: Int) {

        // To be replaced by retrofit code


        val destinationApi = RetrofitClient.buildService(DestinationAPI::class.java)
        val requestCall = destinationApi.getDestination(id)


        requestCall.enqueue(object : Callback<Destination> {

            override fun onResponse(call: Call<Destination>, response: Response<Destination>) {
                if (response.isSuccessful) {
                    val destination = response.body()
                    destination?.let {
                        et_city.setText(destination.city)
                        et_description.setText(destination.description)
                        et_country.setText(destination.country)
                        collapsing_toolbar.title = destination.city
                    }
                } else {
                    showToast("Failed to retrieve details")
                }
            }

            override fun onFailure(call: Call<Destination>, t: Throwable) {
                showToast("Failed server error")
            }

        })



    }

    private fun initUpdateButton(id: Int) {

        btn_update.setOnClickListener {

        }
    }

    private fun initDeleteButton(id: Int) {

        btn_delete.setOnClickListener {



        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            navigateUpTo(Intent(this, DestinationListActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val ARG_ITEM_ID = "item_id"
    }
}
