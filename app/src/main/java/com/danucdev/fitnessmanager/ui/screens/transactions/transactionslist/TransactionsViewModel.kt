package com.danucdev.fitnessmanager.ui.screens.transactions.transactionslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danucdev.fitnessmanager.domain.usecases.transactions.GetAllTransactionsUseCase
import com.danucdev.fitnessmanager.ui.screens.transactions.transactionslist.TransactionsViewMode.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

enum class TransactionsViewMode(val value: String) {
    EARNS("Ingresos"), EXPENSES("Gastos"), ALL("Ver todo")
}

@HiltViewModel
class TransactionsViewModel @Inject constructor(getAllTransactionsUseCase: GetAllTransactionsUseCase) :
    ViewModel() {

    private val _transactionViewMode = MutableStateFlow<TransactionsViewMode>(ALL)
    val transactionsViewMode = _transactionViewMode.asStateFlow()

    private val _transactions =
        combine(transactionsViewMode, getAllTransactionsUseCase()) { viewMode, transactions ->
            when (viewMode) {
                EARNS -> transactions.filter { it.isEarn }
                EXPENSES -> transactions.filter { !it.isEarn }
                ALL -> transactions
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
    val transactions = _transactions


    fun updateTransactionViewMode(newValue: TransactionsViewMode) {
        _transactionViewMode.update { newValue }
    }

}