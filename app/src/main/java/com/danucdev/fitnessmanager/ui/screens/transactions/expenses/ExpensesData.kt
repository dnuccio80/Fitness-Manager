package com.danucdev.fitnessmanager.ui.screens.transactions.expenses

import com.danucdev.fitnessmanager.domain.models.Transaction

data class ExpensesData(
    val details:String = "",
    val amount:Long = 0L,
    val isAllData: Boolean = true
) {
    fun toTransaction(): Transaction {
        return Transaction(
            isEarn = false,
            amount = amount,
            description = details
        )
    }
}
