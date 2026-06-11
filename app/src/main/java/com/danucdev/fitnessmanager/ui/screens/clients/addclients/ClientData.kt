package com.danucdev.fitnessmanager.ui.screens.clients.addclients

import com.danucdev.fitnessmanager.domain.models.Client
import com.danucdev.fitnessmanager.ui.screens.transactions.payments.PaymentMethod
import com.danucdev.fitnessmanager.ui.screens.transactions.payments.PaymentMethod.CASH

data class ClientData(
    val name:String = "",
    val lastName:String = "",
    val phoneNumber:Long = 0L,
    val alreadyPay: Boolean = true,
    val allData: Boolean = true,
    val paymentMethod: PaymentMethod = CASH
) {
    fun toClient(): Client {
        return Client(
            name = name,
            lastName = lastName,
            phone = phoneNumber
        )
    }
}
