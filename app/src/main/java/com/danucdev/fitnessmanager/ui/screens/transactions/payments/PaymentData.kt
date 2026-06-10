package com.danucdev.fitnessmanager.ui.screens.transactions.payments

import com.danucdev.fitnessmanager.data.dao.TransactionDao_Impl
import com.danucdev.fitnessmanager.domain.models.Transaction

data class PaymentData(
    val clientName:String = "",
    val paymentMethod: PaymentMethod = PaymentMethod.CASH,
    val description:String = "",
    val amount:String = "40000"
) {
    fun toTransaction(): Transaction {
        return Transaction(
            isEarn = true,
            amount = amount,
            description = "Pago en ${paymentMethod.method} de $clientName"
        )
    }
}
