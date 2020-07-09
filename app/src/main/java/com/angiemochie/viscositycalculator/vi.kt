package com.angiemochie.viscositycalculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView

import kotlin.math.ln
import kotlin.math.pow
import kotlin.Double as Double


class vi : AppCompatActivity(), TextWatcher {

    // Viscosity Index
    private lateinit var viAnswerStaticTextView: TextView
    private lateinit var vi_40_EditText: EditText
    private lateinit var vi_100_EditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vi)

        initViews()
    }

    private fun initViews(){
        vi_40_EditText = findViewById(R.id.vi_40_EditText)
        vi_100_EditText = findViewById(R.id.vi_100_EditText)
        viAnswerStaticTextView = findViewById(R.id.viAnswerStaticTextView)

        vi_40_EditText.addTextChangedListener(this)
        vi_100_EditText.addTextChangedListener(this)
    }

    private fun calculateVi(){

        val v40 = vi_40_EditText.text.toString().toDouble()
        val v100 = vi_100_EditText.text.toString().toDouble()
        var viscosityIndex : Double

        // Initiate the lookup table
        val yMin : List<Double> = listOf(2.0, 3.8, 4.4, 5.0, 6.4, 7.0, 7.7, 9.0, 12.0,
            15.0, 18.0, 22.0, 28.0, 40.0, 55.0, 70.0)
        val yMax : List<Double> = listOf(3.8, 4.4, 5.0, 6.4, 7.0, 7.7, 9.0,12.0,
            15.0, 18.0, 22.0, 28.0, 40.0, 55.0, 70.0, 71.0)
        val yValues = yMin zip yMax
        val a = listOf(1.14673,3.38095,2.5000,0.10100,3.35714,0.01191,0.41858,0.88779,0.76720,0.97305,0.97256,0.91413,0.87031,0.84703,0.85921,0.83531)
        val b = listOf(1.7576,-15.4952,-7.2143,16.6350,-23.5643,21.4750,16.1558,7.5527,10.7972,5.3135,5.2500,7.4759,9.7157,12.6752,11.1009,14.6731)
        val c = listOf(-0.109,33.196,13.812,-45.469,78.466,-72.870,-56.040,-16.600,-38.180,-2.200,-0.980,-21.820,-50.770,-133.310,-83.19,-216.246)
        val d = listOf(0.84155,0.78571,0.82143,0.04985,0.22619,0.79762,0.05794,0.26665,0.20073,0.28889,0.24504,0.20323,0.18411,0.17029,0.17130,0.16841)
        val e = listOf(1.5521,1.7929,1.5679,9.1613,7.7369,-0.7321,10.5156,6.7015,8.4658,5.9741,7.4160,9.1267,10.1015,11.4866,11.3680,11.8493)
        val f = listOf(-0.077,-0.183,0.119,-18.557,-16.656,14.610,-28.240,-10.810,-22.490,-4.930,-16.730,-34.230,-46.750,-80.620,-76.940,-96.947)

        if (2.0 <= v100 && v100 <= 71.0){
            val yFiltered = yValues.filter{ it.first <= v100 && it.second > v100}
            val yIndex = yValues.indexOf(yFiltered[0])

            val L : Double = a[yIndex].times(v100.pow(2)) + b[yIndex].times(v100) + c[yIndex]
            val H : Double = d[yIndex].times(v100.pow(2)) + e[yIndex].times(v100) + f[yIndex]


            if (v40 >= H){
                viscosityIndex = ((L - v40)/(L - H)).times(100.0)
            } else {
                val N : Double = (ln(H)- ln(v40))/ln(v100)
                viscosityIndex  = (10.0.pow(N) - 1).div(0.00715).plus(100)
            }

            viAnswerStaticTextView.text = String.format("%.2f", viscosityIndex)
        } else {

            viAnswerStaticTextView.text = "Invalid Inputs"
        }
        
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (vi_40_EditText.text.isNotEmpty() and vi_100_EditText.text.isNotEmpty()) {
            calculateVi()
        }
    }

    override fun afterTextChanged(s: Editable?) {}
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

}