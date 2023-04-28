package com.glidline.myglidelinss.activity

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.glidline.myglidelinss.ApiClient
import com.glidline.myglidelinss.model.CustomerlistModel
import com.glidline.myglidelinss.R
import com.glidline.myglidelinss.model.User
import com.glidline.myglidelinss.UserAdapter
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CustomerDetailsList : AppCompatActivity() {
    private lateinit var userAdapter: UserAdapter
    private var userList = ArrayList<User>()
    var sharedpreferences: SharedPreferences? = null
    private val token =
        "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiZjgyYjYyZmFmYmVhN2M4N2U1MGNjNWZjMDJlODY1NTZiZjYwMTEyZDNhODcyY2QzOTdiZTA3OTM3MmM0NzY4YTFmMzQ1MjkzNmM5NTA2NmIiLCJpYXQiOjE2ODI1ODE2MjUuMzI5MDYzLCJuYmYiOjE2ODI1ODE2MjUuMzI5MDY3LCJleHAiOjE3MTQyMDQwMjUuMzIxNTQsInN1YiI6IjIiLCJzY29wZXMiOltdfQ.hC5fi-isUOVr7_BYm4Pck7JVqrDRFXNfRYFCEtWM95kITfzWusyjknT9lB3ZWE7RPFvcwLokZ64NlotfKpKEWQT4bh2ujgskfoC_FNGcXwMSKQrP_devGkHvfCEv3ZZwBKb989ivV7ZUzk3635KzP-zkDXhUZGFNf4uz_8cXnwhrprfD7VO1MzO4xBhCTZ10ZpBhmB4FXHDH7PlA56y1N6az3zKCxvRXBei7iMY3iquVO23RigqABr7N7qFB2WctXjBhD3_E8uaNTnXa4WW2dCsyCAqgHLXLCjpfI8yGL54w6hrGPvWvfuIlcjh-JSe4_ptgJSoLyh5kc5Nwt7xsq4IbCZiP4S7JGIoqVFEinV_Mk4mn4XC4Sp2tfC71uzhxDc10AyRkPntkCQPia5SWTZLAZmriM6DFeXfdo9DxdRDn1U_Bc0Ijk0ph3Bp0AvGZtf0GvoXRaCQAP2zqukkDNdczG0n0ftSqN18wch3fwoQc_Ab4hBtLoHl5pGmY3DqduKQXDdWMWObPU5EolMBQC7jhPMzIDIbdb3V8_DQxj67ccTjDNuYhRGGsiXcmLbiJLBVeZpqZWx90QfYKCCRY0Kk4vObrmGKT9hOb6Rb9z1M9r-p1ICvxl2poqgZL86LfGnOeDVnxhshuAe4pxoEol06vZ0Z2w9VKK4Zhw0CuU1E"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_details_list)

        // Initialize the RecyclerView and UserAdapter
        val recyclerView: RecyclerView = findViewById(R.id.peopleRV)

        recyclerView.layoutManager = LinearLayoutManager(this)

        userAdapter =
            UserAdapter(userList, this@CustomerDetailsList, object : UserAdapter.BtnClickListener {
                override fun onBtnClick(position: Int, type: String) {
                }
            })
        Log.d("TAG", "onCreate39: ${userList.size}")
        recyclerView.adapter = userAdapter
        fetchUsers()
    }

    private fun fetchUsers() {

//        sharedpreferences = getSharedPreferences("login", MODE_PRIVATE)
//       val token =sharedpreferences!!.getString("token","")
//        // set request headers
//        val headers = HashMap<String, String>()
//        headers["Authorization"] = "Bearer $token"

        val apiService = ApiClient.client.getUsers("Bearer $token")
        apiService.enqueue(object : Callback<Map<Any, Any>> {
            override fun onResponse(call: Call<Map<Any, Any>>, response: Response<Map<Any, Any>>) {

                if (response.isSuccessful) {
                    Log.d("getchat60", response.body().toString())
                    val chat = response.body()
                    if (chat != null) {
                        Log.d("getchat62", chat.toString())

                        val gson = Gson()
                        val objrespose =
                            gson.fromJson(gson.toJson(chat), CustomerlistModel::class.java)

                        if (objrespose.status) {
                            userList.addAll(objrespose.users)
                            userAdapter.notifyDataSetChanged()
                        }
                        Log.e("TAG", "onResponse__: " + userList.size)
                    }
                } else {
                    Toast.makeText(this@CustomerDetailsList, response.message(), Toast.LENGTH_SHORT)
                        .show()
                }

            }

            override fun onFailure(call: Call<Map<Any, Any>>, t: Throwable) {
                Toast.makeText(
                    this@CustomerDetailsList,
                    "Network Error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()

            }

        })


    }
}