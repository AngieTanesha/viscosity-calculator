package com.angiemochie.viscositycalculator

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageButton


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val goToViActivity : Button = findViewById(R.id.viButton)
        val goToVbActivity : Button= findViewById(R.id.vbButton)

        goToViActivity.setOnClickListener {
            val intent = Intent(this, vi::class.java)
            startActivity(intent)
        }

        goToVbActivity.setOnClickListener {
            val intent2 = Intent(this, vb::class.java)
            startActivity(intent2)
        }

    }



}

