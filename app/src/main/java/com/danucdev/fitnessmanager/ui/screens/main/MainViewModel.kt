package com.danucdev.fitnessmanager.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danucdev.fitnessmanager.domain.usecases.transactions.GetLastTransactionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(getLastTransactionsUseCase: GetLastTransactionsUseCase): ViewModel() {

    private val _lastTransactions = getLastTransactionsUseCase().stateIn(viewModelScope,
        SharingStarted.WhileSubscribed(5000), emptyList())
    val lastTransactions = _lastTransactions

}