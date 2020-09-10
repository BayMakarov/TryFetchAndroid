package com.fms.ozan.tryfetchandroid

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class MainActivity : AppCompatActivity() {

    private var responseList : List<ResponseModel> = ArrayList()

    private lateinit var list : ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        list = findViewById(R.id.list)

        loadValues()
    }


    private fun loadValues() {

        responseList = ArrayList()


        val destinationService  = ServiceBuilder.buildService(Service::class.java)
        val requestCall =destinationService.getValues()

        requestCall.enqueue(object : Callback<List<ResponseModel>>{

            override fun onFailure(call: Call<List<ResponseModel>>?, t: Throwable?) {

            }

            override fun onResponse(
                call: Call<List<ResponseModel>>?,
                response: Response<List<ResponseModel>>?
            ) {

                if (response != null && response.isSuccessful && response.body() != null) {

                    var tempList: ArrayList<ResponseModel> = ArrayList()

                    for (item in response.body()){

                        val name = item.name

                        if (name != null && name.isNotEmpty()){

                            tempList.add(item)

                        }

                    }

                    responseList = tempList.sortedWith(compareBy(ResponseModel::listId, ResponseModel::id))


                    val adapter = ResponseAdapter(this@MainActivity, responseList)
                    list.adapter = adapter


                }
            }

        })

    }
}

//Interface to call service
interface Service {

    @GET("hiring.json")
    fun getValues () : Call<List<ResponseModel>>
}

//REtrofit Builder
object ServiceBuilder {
    private const val URL ="https://fetch-hiring.s3.amazonaws.com/"
    //CREATE HTTP CLIENT
    private val okHttp = OkHttpClient.Builder()

    //retrofit builder
    private val builder = Retrofit.Builder().baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())

    //create retrofit Instance
    private val retrofit = builder.build()

    //we will use this class to create an anonymous inner class function that
    //implements Country service Interface


    fun <T> buildService (serviceType :Class<T>):T{
        return retrofit.create(serviceType)
    }

}