package com.danucdev.fitnessmanager.ui.screens.clients.addclients

data class ClientData(
    val name:String = "",
    val lastName:String = "",
    val phoneNUmber:String = "",
    val alreadyPay: Boolean = true,
    val allData: Boolean = true
)
