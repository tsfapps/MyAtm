package com.appslelo.myatm

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.appslelo.myatm.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var number: Int = 0
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityMainBinding =  DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val etAmount = activityMainBinding.etAmount
        val btnWithdraw = activityMainBinding.btnWithdraw
        val tvMoney = activityMainBinding.tvMoney


        btnWithdraw.setOnClickListener{
            tvMoney.text = ""
            val inputValue: String = etAmount.text.toString()
            number = inputValue.toInt()
            if (inputValue == null || inputValue.trim()==""){
                Toast.makeText(this,"please input data, edit text cannot be blank",Toast.LENGTH_LONG).show()
            }else if (number>15000)
            {
                Toast.makeText(this,"ATM Cash Limit exceeds.",Toast.LENGTH_LONG).show()
            }else if(number%100>0){
                Toast.makeText(this,"Please insert valid amount",Toast.LENGTH_LONG).show()
            }
            else {
                val denominations = intArrayOf(2000, 500, 200, 100)
                val amount: Int = number
                val count: IntArray = breakdown(denominations, amount)

                for (i in denominations.indices) {
                    if (count[i] > 0) {
                        tvMoney.append(denominations[i].toString()+ " : " + count[i].toString()+"\n")
                    }
                }


            }
        }
    }


private fun breakdown(denominations: IntArray, amount: Int): IntArray {
    var amount = amount
    val count = IntArray(denominations.size)

    for (i in denominations.indices) {  while (amount >= denominations[i]) {
            count[i]++
            amount -= denominations[i]
        }
    }
    return count
}
}
