package com.danucdev.fitnessmanager.ui.screens.transactions.transactionslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danucdev.fitnessmanager.domain.usecases.transactions.GetAllTransactionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(getAllTransactionsUseCase: GetAllTransactionsUseCase) :
    ViewModel() {

    private val _transactions = getAllTransactionsUseCase().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )
    val transactions = _transactions



}