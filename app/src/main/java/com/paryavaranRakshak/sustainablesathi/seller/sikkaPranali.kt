package com.paryavaranRakshak.sustainablesathi.seller

import android.content.Context
import android.widget.Switch
import java.util.Date
import java.util.concurrent.TimeUnit

class sikkaPranali() {

    private lateinit var dop: Date
    private var basePrice: Double = 0.0
    private var condition: Int = -1
    /*
        -1 -> Not declared
         0 -> Top
         1 -> Moderate
         2 -> Worst
         3 -> Not working
     */
    private var priceAfterDep: Double = 0.0
    private var finalPrice: Double = 0.0

    fun getPrice(dateOfPurchase: Date, mrp: Double, conditionOfDevice: Int, quantity: Int): Double{

        dop = dateOfPurchase
        basePrice = mrp
        condition = conditionOfDevice

        getDepPrice()

        when (condition){
            1->
                finalPrice = priceAfterDep - (priceAfterDep * 10/100)
            2->
                finalPrice = priceAfterDep - (priceAfterDep * 30/100)
            3->
                finalPrice = basePrice * 5/100
        }

        return finalPrice * quantity
    }

    private fun getDepPrice() {
        val currentTime = Date()
        val differenceInMillis = currentTime.time - dop.time
        val totalUsedDays = TimeUnit.MILLISECONDS.toDays(differenceInMillis)

        priceAfterDep = if (totalUsedDays < 365){
            basePrice - (basePrice * 20/100)
        } else if ((totalUsedDays > 365) && (totalUsedDays < 830)) {
            basePrice - (basePrice * 40/100)
        } else {
            basePrice - (basePrice * 60/100)
        }

    }

}