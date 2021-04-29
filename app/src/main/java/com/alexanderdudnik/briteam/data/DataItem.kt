package com.alexanderdudnik.briteam.data

import android.graphics.Color

//****************************
//Data class for item of barcode combination
//
//****************************
data class DataItem(
    val barcode: String,
    val type: String,
    val color: String
)
{
    //Return value of color for text representation from field
    fun getColorValue() : Int =
        when (color){
            "Yellow" -> Color.YELLOW
            "Green" -> Color.GREEN
            "Blue" -> Color.BLUE
            "Red" -> Color.RED
            "Black" -> Color.BLACK
            "White" -> Color.WHITE
            else -> Color.LTGRAY
        }


    //function for objects comparison - compare by barcode
    fun compareTo(another:DataItem) = this.barcode.compareTo(another.barcode)

    //check equality of data - check only by barcode and type
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DataItem

        if (barcode != other.barcode) return false
        if (type != other.type) return false

        return true
    }

    //hashcode - all fields
    override fun hashCode(): Int {
        var result = barcode.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + color.hashCode()
        return result
    }


}
