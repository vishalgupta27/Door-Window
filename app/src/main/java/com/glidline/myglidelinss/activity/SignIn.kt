package com.glidline.myglidelinss.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.glidline.myglidelinss.ApiClient
import com.glidline.myglidelinss.model.ModelLogin
import com.glidline.myglidelinss.R
import com.glidline.myglidelinss.utils.isEmailValid
import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignIn : AppCompatActivity() {
    var img_eye: ImageView? = null
    var ed_email: EditText? = null
    var ed_password: EditText? = null
    var sharedpreferences: SharedPreferences? = null
    private val TAG = "SignInTag"
val token ="eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiZjgyYjYyZmFmYmVhN2M4N2U1MGNjNWZjMDJlODY1NTZiZjYwMTEyZDNhODcyY2QzOTdiZTA3OTM3MmM0NzY4YTFmMzQ1MjkzNmM5NTA2NmIiLCJpYXQiOjE2ODI1ODE2MjUuMzI5MDYzLCJuYmYiOjE2ODI1ODE2MjUuMzI5MDY3LCJleHAiOjE3MTQyMDQwMjUuMzIxNTQsInN1YiI6IjIiLCJzY29wZXMiOltdfQ.hC5fi-isUOVr7_BYm4Pck7JVqrDRFXNfRYFCEtWM95kITfzWusyjknT9lB3ZWE7RPFvcwLokZ64NlotfKpKEWQT4bh2ujgskfoC_FNGcXwMSKQrP_devGkHvfCEv3ZZwBKb989ivV7ZUzk3635KzP-zkDXhUZGFNf4uz_8cXnwhrprfD7VO1MzO4xBhCTZ10ZpBhmB4FXHDH7PlA56y1N6az3zKCxvRXBei7iMY3iquVO23RigqABr7N7qFB2WctXjBhD3_E8uaNTnXa4WW2dCsyCAqgHLXLCjpfI8yGL54w6hrGPvWvfuIlcjh-JSe4_ptgJSoLyh5kc5Nwt7xsq4IbCZiP4S7JGIoqVFEinV_Mk4mn4XC4Sp2tfC71uzhxDc10AyRkPntkCQPia5SWTZLAZmriM6DFeXfdo9DxdRDn1U_Bc0Ijk0ph3Bp0AvGZtf0GvoXRaCQAP2zqukkDNdczG0n0ftSqN18wch3fwoQc_Ab4hBtLoHl5pGmY3DqduKQXDdWMWObPU5EolMBQC7jhPMzIDIbdb3V8_DQxj67ccTjDNuYhRGGsiXcmLbiJLBVeZpqZWx90QfYKCCRY0Kk4vObrmGKT9hOb6Rb9z1M9r-p1ICvxl2poqgZL86LfGnOeDVnxhshuAe4pxoEol06vZ0Z2w9VKK4Zhw0CuU1E"
    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        img_eye = findViewById(R.id.img_eye)
        ed_email = findViewById<EditText>(R.id.ed_email)
        ed_password = findViewById<EditText>(R.id.ed_password)

        val btn_signIn = findViewById<Button>(R.id.btn_signIn)
        btn_signIn.setOnClickListener {
            SigInValidation()
//            APiSignIn()
        }

        val tv_sign_up = findViewById<TextView>(R.id.tv_sign_up)
        tv_sign_up.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }
        val tv_forgot = findViewById<TextView>(R.id.tv_forgot)
        tv_forgot.setOnClickListener {
            val intent = Intent(this, VerifyAccount::class.java)
            startActivity(intent)
        }

        img_eye!!.setOnClickListener {
            if (ed_password!!.transformationMethod == PasswordTransformationMethod.getInstance()) {

                img_eye!!.setImageDrawable(resources.getDrawable(R.drawable.view))
                ed_password!!.transformationMethod = HideReturnsTransformationMethod.getInstance()

            } else {

                img_eye!!.setImageDrawable(resources.getDrawable(R.drawable.resource_private))
                ed_password!!.transformationMethod = PasswordTransformationMethod.getInstance()

            }
        }

        sharedpreferences = getSharedPreferences("login", MODE_PRIVATE)
        val isLoggedIn = sharedpreferences?.getBoolean("isLoggedIn", false)
        if (isLoggedIn == true) {
            // Navigate to dashboard screen
            val intent = Intent(this, dashboard::class.java)
            startActivity(intent)
            finish()
        }


    }

    fun SigInValidation() {

        if (ed_email!!.text.toString().isEmpty() && !isEmailValid(ed_email!!.text.toString())) {
            Toast.makeText(this, "Enter Valid Email", Toast.LENGTH_SHORT).show()
        }


        if (ed_password!!.text.toString().isEmpty()) {
            Toast.makeText(this, "Enter valid Password", Toast.LENGTH_SHORT).show()
        } else {
            APiSignIn()
        }

    }

    private fun APiSignIn() {
        val signup = ApiClient.client.login("avantika@augurs.in", "12345678")
        signup.enqueue(object : Callback<Map<Any, Any>> {
            override fun onResponse(
                call: Call<Map<Any, Any>>,
                response: Response<Map<Any, Any>>
            ) {
                val chat = response.body()
                Log.d("getchat127", chat.toString())
                if (chat != null) {

                    val jsonObject = JSONObject(chat)
                    val gson = Gson()
                    val objrespose = gson.fromJson(jsonObject.toString(), ModelLogin::class.java)
                    if (objrespose.status == true) {

                        // Store login credentials in SharedPreferences
                        sharedpreferences = getSharedPreferences("login", MODE_PRIVATE)
                        val editor = sharedpreferences!!.edit()
                        editor.putString("email", "avantika@augurs.in")
                        editor.putString("password", "12345678")
                        editor.putString("token", token)
                        editor.putBoolean("isLoggedIn", true)
                        editor.apply()
                        Log.d("TAG", "onCreate:128 $editor")

                        val intent = Intent(this@SignIn, dashboard::class.java)
                        startActivity(intent)

                    }
                    Log.d("Meetprofiles", "onResponse:${objrespose.profile}")
                    Toast.makeText(this@SignIn, "You are successfully login ", Toast.LENGTH_SHORT).show()
                }

            }
            override fun onFailure(call: Call<Map<Any, Any>>, t: Throwable) {
                Log.i("errorMessage", t.message!!)
                Toast.makeText(this@SignIn, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

}
