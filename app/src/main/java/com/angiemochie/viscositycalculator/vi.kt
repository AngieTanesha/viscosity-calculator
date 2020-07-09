package com.angiemochie.viscositycalculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import krangl.dataFrameOf
import krangl.ge
import krangl.lt
import kotlin.math.ln
import kotlin.math.pow


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
        val v40 : Double = vi_40_EditText.text.toString().toDouble()
        val v100 : Double = vi_100_EditText.text.toString().toDouble()
        val VI : Double

        val df = dataFrameOf("Ymin","Ymax","a","b","c","d","e","f")(
            2.0,3.8,1.14673,1.7576,-0.109,0.84155,1.5521,-0.077,
            3.8,4.4,3.38095,-15.4952,33.196,0.78571,1.7929,-0.183,
            4.4,5.0,2.5000,-7.2143,13.812,0.82143,1.5679,0.119,
            5.0,6.4,0.10100,16.6350,-45.469,0.04985,9.1613,-18.557,
            6.4,7.0,3.35714,-23.5643,78.466,0.22619,7.7369,-16.656,
            7.0,7.7,0.01191,21.4750,-72.870,0.79762,-0.7321,14.610,
            7.7,9.0,0.41858,16.1558,-56.040,0.05794,10.5156,-28.240,
            9.0,12.0,0.88779,7.5527,-16.600,0.26665,6.7015,-10.810,
            12.0,15.0,0.76720,10.7972,-38.180,0.20073,8.4658,-22.490,
            15.0,18.0,0.97305,5.3135,-2.200,0.28889,5.9741,-4.930,
            18.0,22.0,0.97256,5.2500,-0.980,0.24504,7.4160,-16.730,
            22.0,28.0,0.91413,7.4759,-21.820,0.20323,9.1267,-34.230,
            28.0,40.0,0.87031,9.7157,-50.770,0.18411,10.1015,-46.750,
            40.0,55.0,0.84703,12.6752,-133.310,0.17029,11.4866,-80.620,
            55.0,70.0,0.85921,11.1009,-83.19,0.17130,11.3680,-76.940,
            70.0,71.0,0.83531,14.6731,-216.246,0.16841,11.8493,-96.94)

        if (v100 in 2.0..71.0){
            val eqn = df.filter{ it["Ymin"] lt v100 }.filter{ it["Ymax"] ge v100}

            val L : Double = eqn["a"][0].toString().toDouble().times(v100.pow(2)) + eqn["b"][0].toString().toDouble().times(v100) + eqn["c"][0].toString().toDouble()
            val H : Double = eqn["d"][0].toString().toDouble().times(v100.pow(2)) + eqn["e"][0].toString().toDouble().times(v100) + eqn["f"][0].toString().toDouble()

            if (v40 <= H){
                VI = ((L - v40)/(L - H)).times(v100)
            } else {
                val N : Double = (ln(H)- ln(v40))/ln(v100)
                VI  = (10.0.pow(N) - 1).div(0.00715).plus(100)
            }

            viAnswerStaticTextView.text = String.format("%.2f", VI)

        }

        viAnswerStaticTextView.text = String.format("Invalid inputs")

        
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (vi_40_EditText.text.isNotEmpty() and vi_100_EditText.text.isNotEmpty()) {
            calculateVi()
        }
    }

    override fun afterTextChanged(s: Editable?) {}
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

}