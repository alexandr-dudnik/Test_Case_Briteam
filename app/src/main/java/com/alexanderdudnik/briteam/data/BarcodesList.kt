package com.alexanderdudnik.briteam.data

import java.util.*

//****************************
//Data class for list of barcodes
//
//****************************
data class BarcodesList(
    var items: MutableList<ListItem> = mutableListOf()
){
    //Add an item to list and then sort a list
    fun addItem(item: DataItem){
        items.add(ListItem(data = item, dateTime = Date()))
        items.sortBy{ it.data.barcode }
    }
}