package com.danucdev.fitnessmanager.ui.screens.transactions.expenses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danucdev.fitnessmanager.domain.models.Transaction
import com.danucdev.fitnessmanager.domain.usecases.transactions.AddTransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpensesViewModel @Inject constructor(
    private val addTransactionUseCase: AddTransactionUseCase
): ViewModel() {

    private val _expenseData = MutableStateFlow(ExpensesData())
    val expenseData = _expenseData.asStateFlow()

    private val _events = MutableSharedFlow<String>(extraBufferCapacity = 1)
    val events = _events.asSharedFlow()

    fun updateDetails(newValue:String) {
        _expenseData.update { current ->
            current.copy(details = newValue)
        }
    }

    fun updateAmount(newValue: String) {
        _expenseData.update { current ->
            current.copy(amount = newValue)
        }
    }

    fun addExpense() {
        if(isAllData()) {
            viewModelScope.launch(Dispatchers.IO) {
                addTransactionUseCase(_expenseData.value.toTransaction())
                _events.emit("Guardado con éxito!")
                cleanData()
            }
        } else {
            _expenseData.update { current ->
                current.copy(isAllData = false)
            }
        }
    }

    private fun cleanData() {
        _expenseData.update { current ->
            current.copy(details = "", amount = "", isAllData = true)
        }
    }

    private fun isAllData(): Boolean {
        return _expenseData.value.details.isNotBlank() && _expenseData.value.amount.isNotBlank()
    }


}