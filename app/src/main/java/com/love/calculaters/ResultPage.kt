package com.love.calculaters

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class ResultPage : AppCompatActivity() {
    private lateinit var yourNameTxt:TextView
    private lateinit var yourGfNameTxt:TextView
    private lateinit var perTxt:TextView
    private lateinit var messageTxt:TextView
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_page)

        yourNameTxt = findViewById(R.id.your_nameTxt)
        yourGfNameTxt = findViewById(R.id.your_lover_nameTxt)
        perTxt = findViewById(R.id.per)
        messageTxt = findViewById(R.id.msg)


        val intent = intent
        val extras = intent.extras

        if (extras != null){
            yourNameTxt.text = extras.getString("yourname")
            yourGfNameTxt.text = extras.getString("yourgfname")
            perTxt.text = extras.getString("per")+"%"
            messageTxt.text = extras.getString("msg")
        }else{
            Log.d("TAG","value is null")
        }
    }
}