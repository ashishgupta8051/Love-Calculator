package com.love.calculaters

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.squareup.okhttp.*
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var yournameEdt:EditText
    private lateinit var gfNameEdt:EditText
    private lateinit var checkBtn:ImageView
    private lateinit var yourNameStr:String
    private lateinit var gfNameStr:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        yournameEdt = findViewById(R.id.your_name)
        gfNameEdt = findViewById(R.id.gf_name)
        checkBtn = findViewById(R.id.check)

        checkBtn.setOnClickListener {
            yourNameStr = yournameEdt.text.toString()
            gfNameStr = gfNameEdt.text.toString()
            when {
                yourNameStr.isEmpty() -> {
                    yournameEdt.requestFocus()
                    yournameEdt.error = "Enter Your Name"
                }
                gfNameStr.isEmpty() -> {
                    gfNameEdt.requestFocus()
                    gfNameEdt.error = "Enter your Lover Name"
                }
                else -> {
                    getResult(yourNameStr,gfNameStr)
                }
            }
        }
    }

    private fun getResult(yourNameStr: String, gfNameStr: String) {

        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://love-calculator.p.rapidapi.com/getPercentage?fname=$yourNameStr&sname=$gfNameStr")
            .get()
            .addHeader("x-rapidapi-key", "f2e9514b23mshafb9591da2f31d4p1dbc5bjsn2241d10a6b53")
            .addHeader("x-rapidapi-host", "love-calculator.p.rapidapi.com")
            .build()

        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(request: Request?, e: IOException?) {
                Toast.makeText(this@MainActivity,"Error".toString(),Toast.LENGTH_SHORT).show()
            }

            @SuppressLint("SetTextI18n")
            override fun onResponse(response: Response?) {
                if (response!!.isSuccessful){
                    this@MainActivity.runOnUiThread {
                        val res = response.body().string()
                        val jsonObject = JSONObject(res)
                        val result = jsonObject.getString("result")
                        val per = jsonObject.getString("percentage")
                        val intent = Intent(this@MainActivity,ResultPage::class.java)
                        intent.putExtra("yourname",yourNameStr)
                        intent.putExtra("yourgfname",gfNameStr)
                        intent.putExtra("per",per)
                        intent.putExtra("msg",result)
                        startActivity(intent)
                    }
                }else{
                    Toast.makeText(this@MainActivity,response.message().toString(),Toast.LENGTH_SHORT).show()
                }
            }

        })
    }
}