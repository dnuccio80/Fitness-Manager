package com.danucdev.fitnessmanager.ui.screens.transactions.payments

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danucdev.fitnessmanager.domain.models.ProductService
import com.danucdev.fitnessmanager.domain.repositories.ProductServiceRepository
import com.danucdev.fitnessmanager.domain.usecases.clients.GetAllClientsUseCase
import com.danucdev.fitnessmanager.domain.usecases.productservices.GetProductServiceByIdUseCase
import com.danucdev.fitnessmanager.domain.usecases.transactions.AddTransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentsViewModel @Inject constructor(
    getAllClientsUseCase: GetAllClientsUseCase,
    private val addTransactionUseCase: AddTransactionUseCase,
    productServiceRepository: ProductServiceRepository,
    private val getProductServiceByIdUseCase: GetProductServiceByIdUseCase,
) : ViewModel() {

    val productServicesChart = MutableStateFlow<List<ProductService>>(emptyList())

    private val _clientsList = getAllClientsUseCase().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )
    val clientsList = _clientsList

    private val _paymentData = MutableStateFlow(PaymentData())
    val paymentData = _paymentData.asStateFlow()

    private val _productServices = productServiceRepository.getAllProductServices().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )
    val productServices = _productServices

    val productServicesTotal: StateFlow<Long> = productServicesChart.map { list ->
        Log.i("Damian", "Lista: $list")
        list.sumOf { it.amount }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0L)

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

    fun updateAmount() {
        _paymentData.update { current ->
            current.copy(amount = productServicesTotal.value)
        }
    }

    fun addPayment() {
        if (!isAllData()) {
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
                amount = 0
            )
        }
        productServicesChart.value = emptyList()
    }

    fun addItemToChart(id:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val productService = async { getProductServiceByIdUseCase(id) }.await()
            productServicesChart.value += productService
            updateAmount()
        }
    }

    fun deleteItemFromChart(productService: ProductService) {
        productServicesChart.value -= productService
        updateAmount()
    }


    private fun isAllData(): Boolean {
        return _paymentData.value.clientName.isNotBlank() &&
                _paymentData.value.amount > 0
    }


}