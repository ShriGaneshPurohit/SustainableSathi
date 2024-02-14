package com.paryavaranRakshak.sustainablesathi.seller

import android.content.Context
import android.widget.Switch
import java.util.Date
import java.util.concurrent.TimeUnit

class sikkaPranali(context: Context) {

    private lateinit var dop: Date
    private var basePrice: Double = 0.0
    private var condition: Int = -1

    fun getPrice(dateOfPurchase: Date, mrp: Double, conditionOfDevice: Int){

        dop = dateOfPurchase
        basePrice = mrp
        condition = conditionOfDevice

        getDepPrice()

    }

    private fun getDepPrice() {
        val currentTime = Date()
        val differenceInMillis = currentTime.time - dop.time
        val totalUsedDays = TimeUnit.MILLISECONDS.toDays(differenceInMillis)

        basePrice = if (totalUsedDays < 365){
            basePrice - (basePrice * 20/100)
        } else if ((totalUsedDays > 365) && (totalUsedDays < 830)) {
            basePrice - (basePrice * 40/100)
        } else {
            basePrice - (basePrice * 60/100)
        }

    }

}