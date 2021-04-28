Random Scan application

Description
This task consists of creating simple application utilizing basic Android components and features. Application will
generate random values, based on predefined lists and render screens according to instruction. Here are basic
requirements for the task:
- Use Kotlin or Java (as preferred) and Android Studio.
- Add at least one class in the second language (so if you choose to use mainly Kotlin, please add at least one class in Java
or vice versa). This class should have some logic and not be only a model.
- The app should work in landscape and portrait (so the screen will rotate).
- The app should support at least API 21.
- Write unit tests for at least one class.
- As a result, please send us a link to the repository with your project.

Part A (Obligatory)
- Every 5 sec. some producer will produce the next value with random Barcode value, random Barcode type and random
color combination from defined lists:
private val barcodes = listOf("PK01", "BARCODE_A", "4354834", "PO14785-20171215")
private val types = listOf("EAN8", "UPCE", "EAN13", "I25", "QRCODE", "CODE128")
private val colors = listOf("Yellow", "Green", "Blue", "Red", "Black", "White")
- Producer should emit new random values ONLY if the app is in the foreground!
- Master Screen of the app will be a list of views represented by:
first TextView (text = barcode value, textColor = color),
second TextView with barcode type
third TextView with Date & Time when random value was generated.
- The list should be sorted alphabetically by the barcode value
- When new item is emitted, it should be added to the sorted list in the proper place
- On item click, the Toolbar should change its color to the selected one and the text on it should change (barcode value
as a title, and barcode type as a subtitle).