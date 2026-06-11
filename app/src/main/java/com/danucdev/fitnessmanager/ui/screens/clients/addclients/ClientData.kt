package com.danucdev.fitnessmanager.ui.screens.clients.addclients

import com.danucdev.fitnessmanager.domain.models.Client

data class ClientData(
    val name:String = "",
    val lastName:String = "",
    val phoneNumber:Long = 0L,
    val alreadyPay: Boolean = true,
    val allData: Boolean = true
) {
    fun toClient(): Client {
        return Client(
            name = name,
            lastName = lastName,
            phone = phoneNumber
        )
    }
}
