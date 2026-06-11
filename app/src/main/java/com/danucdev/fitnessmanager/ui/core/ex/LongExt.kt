package com.danucdev.fitnessmanager.ui.core.ex

import java.text.NumberFormat
import java.util.Locale

fun Long.toPrice():String {
    val formatter = NumberFormat.getCurrencyInstance(Locale("es", "AR"))
    formatter.maximumFractionDigits = 0
    formatter.minimumFractionDigits = 0

    return formatter.format(this)
}