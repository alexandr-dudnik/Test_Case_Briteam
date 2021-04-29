package com.alexanderdudnik.briteam.extentions

import java.text.SimpleDateFormat
import java.util.*

//****************************
//Extension for Date object
//
//****************************

//format given date by given pattern
fun Date.format(pattern:String="dd.MM.yy HH:mm:ss"):String{
    val dateFormat = SimpleDateFormat(pattern, Locale("en"))

    return dateFormat.format(this)
}
