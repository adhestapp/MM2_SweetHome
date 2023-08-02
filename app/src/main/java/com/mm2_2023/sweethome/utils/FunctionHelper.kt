package com.mm2_2023.sweethome.utils

import java.text.DecimalFormat

object FunctionHelper {
    fun rupiahFormat(price: Int): String {
        val formatter = DecimalFormat("#,###")
        return "Rp " + formatter.format(price.toLong()).replace(",", ".")
    }
}