package com.appslelo.myatm

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.appslelo.myatm.databinding.ActivityMainBinding
import java.util.*
import java.util.function.BiFunction


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

            if (etAmount.text.toString() != "") {
                val inputValue: String = etAmount.text.toString()
                number = inputValue.toInt()
                if (inputValue == null || inputValue.trim() == "") {
                    Toast.makeText(
                        this,
                        "please input data, edit text cannot be blank",
                        Toast.LENGTH_LONG
                    ).show()
                } else if (number > 15000) {
                    Toast.makeText(this, "ATM Cash Limit exceeds.", Toast.LENGTH_LONG).show()
                } else if (number % 100 != 0) {
                    Toast.makeText(this, "Please insert valid amount", Toast.LENGTH_LONG).show()
                } else {

                    val notes = intArrayOf(100, 200, 500, 2000)
                    val amount: Int = number

                    val hashMap = HashMap<String, Int>()
                    if (hasLowestDenomination(notes, 0, notes.size, amount, hashMap)) {
                        for (i in 0..2) {
                            val n = hashMap[notes[i].toString()]!!
                            if (n * notes[i] >= notes[i + 1]) {
                                hashMap.replace(
                                    notes[i].toString(),
                                    n * notes[i] / notes[i + 1]
                                )
                                hashMap.replace(
                                    notes[i + 1].toString(),
                                    hashMap[notes[i + 1].toString()]!! + n * notes[i] / notes[i + 1]
                                )
                            }
                        }
                        for ((key, value) in hashMap) {
                            if (value == 1 || value == 0) {
                                tvMoney.append("$value note of Rs.$key\n")
                            } else {
                                tvMoney.append("$value note of Rs.$key\n")
                                println("$value notes of Rs.$key")
                            }
                        }
                    }

                }
            }else{
                Toast.makeText(this,"please enter the amount", Toast.LENGTH_LONG).show()
            }
        }
    }



    private fun hasLowestDenomination( notes: IntArray , i: Int,  length: Int, sum: Int,  hashMap: HashMap<String, Int>): Boolean {
        if (i == length) return false
        if (sum < 0) return false
        if (i == length - 1 && notes[i] != sum) return false
        hashMap.compute(
            notes[i].toString(),
            BiFunction<String, Int?, Int?> { key: String?, `val`: Int? -> if (`val` == null) 1 else `val` + 1 }
        )
        if (notes[i] == sum) return true
        if (hasLowestDenomination(
                notes,
                i + 1,
                length,
                sum - notes[i],
                hashMap
            ) || hasLowestDenomination(notes, i, length, sum - notes[i], hashMap)
        ) return true
        hashMap.compute(
            notes[i].toString(),
            BiFunction<String, Int?, Int?> { key: String?, `val`: Int? -> if (`val` == null) 0 else `val` - 1 }
        )
        return false
    }





}
