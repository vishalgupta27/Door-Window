package com.glidline.myglidelinss.activity

import android.annotation.SuppressLint
import android.content.Intent
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
import com.glidline.myglidelinss.model.ModelRegister
import com.glidline.myglidelinss.R
import com.glidline.myglidelinss.utils.isEmailValid
import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUp : AppCompatActivity() {
    var img_eye: ImageView? = null
    var img_eyeC: ImageView? = null
    var ed_name: EditText? = null
    var ed_nameLast: EditText? = null
    var ed_email: EditText? = null
    var ed_phone_number: EditText? = null
    var ed_password: EditText? = null
    var ed_cPassword: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        img_eye = findViewById(R.id.img_eye)
        img_eyeC = findViewById(R.id.img_eyeC)

        ed_name = findViewById<EditText>(R.id.ed_name)
        ed_nameLast = findViewById<EditText>(R.id.ed_nameLast)
        ed_email = findViewById<EditText>(R.id.ed_email)
        ed_phone_number = findViewById<EditText>(R.id.ed_phone_number)
        ed_password = findViewById<EditText>(R.id.ed_password)
        ed_cPassword = findViewById<EditText>(R.id.ed_cPassword)

        val btn_sign_up = findViewById<Button>(R.id.btn_sign_up)

        val tv_sign_in = findViewById<TextView>(R.id.tv_sign_in)

        tv_sign_in.setOnClickListener {
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
        }

        btn_sign_up.setOnClickListener {
            SiginValidation()

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
        img_eyeC!!.setOnClickListener {
            if (ed_cPassword!!.transformationMethod == PasswordTransformationMethod.getInstance()) {

                img_eyeC!!.setImageDrawable(resources.getDrawable(R.drawable.view))
                ed_cPassword!!.transformationMethod = HideReturnsTransformationMethod.getInstance()

            } else {

                img_eyeC!!.setImageDrawable(resources.getDrawable(R.drawable.resource_private))
                ed_cPassword!!.transformationMethod = PasswordTransformationMethod.getInstance()

            }
        }

    }

    fun SiginValidation() {
        if (ed_name!!.text.toString().isEmpty()) {
            Toast.makeText(this, "Enter Valid First Name", Toast.LENGTH_SHORT).show()
        }

        if (ed_nameLast!!.text.toString().isEmpty()) {
            Toast.makeText(this, "Enter Valid Last Name", Toast.LENGTH_SHORT).show()
        }
        if (ed_email!!.text.toString().isEmpty() && !isEmailValid( ed_email!!.text.toString())) {
            Toast.makeText(this, "Enter Valid Email", Toast.LENGTH_SHORT).show()
        }

        if (ed_phone_number!!.text.toString().length > 13 || ed_phone_number!!.text.toString().length < 9){
            Toast.makeText(this, "Enter Phone Number", Toast.LENGTH_SHORT).show()
        }

        if (ed_password!!.text.toString().length > 8 ){
            Toast.makeText(this, "Enter at least 8 length", Toast.LENGTH_SHORT).show()
        }
        if (ed_cPassword!!.text.toString().length > 8  ){

            Toast.makeText(this, "Enter at least 8 length", Toast.LENGTH_SHORT).show()
        }
            else if (ed_password!!.text.toString() != ed_cPassword!!.text.toString()) {
            Toast.makeText(this, "Password Mismatch", Toast.LENGTH_SHORT).show()
        }
        else{
            APiSignUp()
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun APiSignUp(){
        val signup = ApiClient.client.register (ed_name!!.text.toString(),ed_nameLast!!.text.toString(),ed_email!!.text.toString(),ed_phone_number!!.text.toString(),
            ed_password!!.text.toString(),ed_cPassword!!.text.toString(),1,"abc" )
        signup.enqueue(object : Callback<Map<Any , Any>> {
            override fun onResponse(
                call: Call<Map<Any, Any>>,
                response: Response<Map<Any, Any>>
            ) {
                val chat = response.body()
                Log.d("getchat127", chat.toString())
                if (chat != null) {

                    val jsonObject = JSONObject(chat)
                    Log.d("getchat_136", jsonObject.toString())
                    val gson = Gson()
                    val objrespose = gson.fromJson(jsonObject.toString(), ModelRegister::class.java)
                    if (objrespose.status == true) {
                        ed_name!!.setText(objrespose.profile.first_name)
                        ed_nameLast!!.setText(objrespose.profile.last_name)
                        ed_email!!.setText(objrespose.profile.email)
                        ed_phone_number!!.setText(objrespose.profile.mobile_number)

                    }
                    Log.d("Meetprofiles", "onResponse:${objrespose.profile}")
                    Toast.makeText(this@SignUp, "You are successfully registered please verify your email for login ",
                        Toast.LENGTH_SHORT).show()


                }

            }

            override fun onFailure(call: Call<Map<Any, Any>>, t: Throwable) {
                Log.i("errorMessage", t.message!!)
                Toast.makeText(this@SignUp, t.message, Toast.LENGTH_SHORT).show()
            }

        })

    }
}
