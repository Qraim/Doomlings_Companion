package com.qraim.doomlingscompanion

class Cards(
    val img: String,
    val name: String,
    val str: String,
    val category: String,    // New field
    val subcategory: String  // New field
) {

    private val image = img
    private val nom = name
    private val traduction = str
    var isVisible: Boolean = true


    fun getimg(): String {
        return image
    }

    fun getstr(): String {
        return traduction
    }

    fun getname(): String {
        return name
    }

}


