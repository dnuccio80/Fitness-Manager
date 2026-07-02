package com.danucdev.fitnessmanager.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danucdev.fitnessmanager.domain.usecases.main.GetTransactionsResumeUseCase
import com.danucdev.fitnessmanager.domain.usecases.productservices.GetMonthlyUseCase
import com.danucdev.fitnessmanager.domain.usecases.transactions.GetLastTransactionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getLastTransactionsUseCase: GetLastTransactionsUseCase,
    getTransactionsResumeUseCase: GetTransactionsResumeUseCase,
    getMonthlyUseCase: GetMonthlyUseCase,
) : ViewModel() {

    private val _lastTransactions = getLastTransactionsUseCase().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000), emptyList()
    )
    val lastTransactions = _lastTransactions

    private val _resume = combine(
        getTransactionsResumeUseCase(),
        getMonthlyUseCase()
    ) { transactions, monthly ->

        MainData(
            totalEarns = transactions.totalEarns,
            totalExpenses = transactions.totalExpenses,
            activeClients = transactions.activeClients,
            monthlyValue = monthly.amount
        )

    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        MainData()
    )


    val resume = _resume


}