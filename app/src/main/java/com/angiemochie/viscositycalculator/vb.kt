package com.angiemochie.viscositycalculator

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.lang.Math.sqrt
import kotlin.math.pow


class vb : AppCompatActivity(), View.OnClickListener {

    // Viscosity Blending
    private lateinit var volumeEditText: EditText
    private lateinit var viscosityEditText: EditText
    private lateinit var vbAnswerStaticTextView: TextView
    private lateinit var vbCalculateButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vb)

        initViews()
    }

    private fun initViews(){
        volumeEditText = findViewById(R.id.vb_Volume_EditText)
        viscosityEditText = findViewById(R.id.vb_Viscosity_EditText)
        vbAnswerStaticTextView = findViewById(R.id.vbAnswerStaticTextView)
        vbCalculateButton = findViewById(R.id.vbCalculateButton)

        vbCalculateButton.setOnClickListener(this)
    }

    private fun calculateVB(){
        val volumeList = volumeEditText.text.toString().split(",").map{ it.trim().toDouble() }
        val viscosityList = viscosityEditText.text.toString().split(",").map{ it.trim().toDouble() }

        val numerator = volumeList.sum()
        val denominator = (volumeList.indices).map { volumeList[it] / sqrt(viscosityList[it]) }.sum()

        val result = (numerator/denominator).pow(2)

        vbAnswerStaticTextView.text = String.format("%.2f", result)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.vbCalculateButton -> calculateVB()
        }
    }


}

