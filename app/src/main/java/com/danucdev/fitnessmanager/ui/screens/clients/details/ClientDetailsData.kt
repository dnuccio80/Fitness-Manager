package com.danucdev.fitnessmanager.ui.screens.clients.details

import java.util.Date

data class ClientDetailsData(
    val name:String = "",
    val lastname:String = "",
    val phoneNumber:Long = 0,
    val alreadyPay: Boolean,
    val lastPaymentDate: Date
)
