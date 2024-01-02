package com.qraim.doomlingscompanion

class Cards(
    val img: String,
    val str: String,
    val name: String
) {

    private val image = img
    private val nom = name
    private val traduction = str

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


