package com.danucdev.fitnessmanager.ui.core.ex

import java.text.NumberFormat
import java.util.Locale

fun String.toPrice():String {
    val formatter = NumberFormat.getCurrencyInstance(Locale("es", "AR"))
    formatter.maximumFractionDigits = 0
    formatter.minimumFractionDigits = 0
    val value = if(this.isNotBlank()) {
        this.toInt()
    } else 0
    return formatter.format(value)
}