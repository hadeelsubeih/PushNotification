package com.example.myapplicationpushnotification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException

class MainActivity : AppCompatActivity() {
    class Login : AppCompatActivity() {
        var requestQueue: RequestQueue? = null
        var email: EditText? = null
        var password: EditText? = null
        var loginButton: Button? = null
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            email = findViewById<View>(R.id.EditTextemail) as EditText
            password = findViewById<View>(R.id.EditTextpass) as EditText
            loginButton = findViewById<View>(R.id.button2) as Button

            if (intent != null) {
                var email1 = intent.getStringExtra("email").toString()
                var password1 = intent.getStringExtra("password").toString()
                loginButton!!.setOnClickListener {
                    val data = "{" +
                            "\"email\"" + ":" + "\"" + email!!.text.toString() + "\"," +
                            "\"password\"" + ":" + "\"" + password!!.text.toString() + "\"" +
                            "}"
                    if (email!!.text.toString() == email1 && password!!.text.toString() == password1) {
                        Submit(data)
                    } else {
                        Toast.makeText(this, "Not values", Toast.LENGTH_LONG).show()
                    }
                }
            }


        }

        private fun Submit(data: String) {

            val URL = "https://mcc-users-api.herokuapp.com/login"
            requestQueue = Volley.newRequestQueue(applicationContext)
            Log.d("TAG", "requestQueue: $requestQueue")
            val stringRequest: StringRequest = object :
                StringRequest(Request.Method.POST, URL, Response.Listener<String?> { response ->
                    try {
                        val jsonObject = JSONObject(response)

                        Log.e("TAG", "onResponse: $jsonObject")
                    } catch (e: JSONException) {
                        Log.e("TAG", "Server Error ")
                    }
                }, Response.ErrorListener { error -> Log.e("TAG", "onErrorResponse: $error") }) {
                override fun getBodyContentType(): String {
                    return "application/json; charset=utf-8"
                }

                @Throws(AuthFailureError::class)
                override fun getBody(): ByteArray {
                    return try {

                        Log.e("TAG", "savedata: $data")
                        if (data == null) null else data.toByteArray(charset("utf-8"))
                    } catch (e: UnsupportedEncodingException) {
                        null
                    }!!
                }
            }
            requestQueue!!.add(stringRequest)
        }
    }
}