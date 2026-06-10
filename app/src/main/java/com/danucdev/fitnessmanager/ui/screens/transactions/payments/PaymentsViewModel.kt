package com.danucdev.fitnessmanager.ui.screens.transactions.payments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danucdev.fitnessmanager.domain.usecases.clients.GetAllClientsUseCase
import com.danucdev.fitnessmanager.domain.usecases.transactions.AddTransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentsViewModel @Inject constructor(
    getAllClientsUseCase: GetAllClientsUseCase,
    private val addTransactionUseCase: AddTransactionUseCase
) : ViewModel() {

    private val _clientsList = getAllClientsUseCase().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )
    val clientsList = _clientsList

    private val _paymentData = MutableStateFlow(PaymentData())
    val paymentData = _paymentData.asStateFlow()

    private val _events = MutableSharedFlow<String>(extraBufferCapacity = 1)
    val events = _events.asSharedFlow()

    fun updateClientName(newValue: String) {
        _paymentData.update { current ->
            current.copy(clientName = newValue)
        }
    }

    fun updatePaymentMethod(newValue: PaymentMethod) {
        _paymentData.update { current ->
            current.copy(paymentMethod = newValue)
        }
    }

    fun addPayment() {
        if(!isAllData()){
            viewModelScope.launch {
                _events.emit("Faltan rellenar datos!")
            }
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                addTransactionUseCase(_paymentData.value.toTransaction())
                _events.emit("Cobro agregado con éxito!")
                cleanData()
            }
        }
    }

    private fun cleanData() {
        _paymentData.update { current ->
            current.copy(
                clientName = "",
                paymentMethod = PaymentMethod.CASH,
                description = "",
                amount = ""
            )
        }
    }

    private fun isAllData(): Boolean {
        return _paymentData.value.clientName.isNotBlank() &&
                _paymentData.value.amount.isNotBlank()
    }


}