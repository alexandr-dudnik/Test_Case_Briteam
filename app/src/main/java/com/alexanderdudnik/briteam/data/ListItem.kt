package com.alexanderdudnik.briteam.data

import java.util.*

//****************************
//Data class for barcode list items
//
//****************************
data class ListItem(
    val data: DataItem,
    val dateTime: Date

) {
    override fun hashCode(): Int {
        return data.hashCode()*31 + dateTime.hashCode();
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ListItem

        if (data != other.data) return false
        if (dateTime != other.dateTime) return false

        return true
    }


}
