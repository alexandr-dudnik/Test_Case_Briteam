package com.alexanderdudnik.briteam.data

//****************************
//Object class for generating items
//
//****************************
object DataGenerator{
    private val barcodes: List<String> = listOf("PK01", "BARCODE_A", "4354834", "PO14785-20171215")
    private val types: List<String> = listOf("EAN8", "UPCE", "EAN13", "I25", "QRCODE", "CODE128")
    private val colors: List<String> = listOf("Yellow", "Green", "Blue", "Red", "Black", "White")

    //generate an item with random barcode, type and color
    fun generateItem() : DataItem {
        return DataItem(
            barcode = barcodes.random(),
            type = types.random(),
            color = colors.random()
        )
    }
}