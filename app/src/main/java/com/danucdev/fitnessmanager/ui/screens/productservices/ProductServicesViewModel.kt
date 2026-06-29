package com.danucdev.fitnessmanager.ui.screens.productservices

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danucdev.fitnessmanager.domain.models.ProductService
import com.danucdev.fitnessmanager.domain.repositories.ProductServiceRepository
import com.danucdev.fitnessmanager.domain.usecases.productservices.GetProductServiceByIdUseCase
import com.danucdev.fitnessmanager.ui.screens.clients.productservices.AddProductServicesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductServicesViewModel @Inject constructor(
    productServiceRepository: ProductServiceRepository,
    private val addProductServicesUseCase: AddProductServicesUseCase,
    private val getProductServiceByIdUseCase: GetProductServiceByIdUseCase,
) : ViewModel() {

    private val productServiceData = MutableStateFlow(
        ProductService(
            label = "",
            amount = 0L
        )
    )

    private val _uiState = combine(
        productServiceRepository.getAllProductServices(),
        productServiceData
    ) { productServicesList, newProductData ->
        ProductServiceUiState(
            isLoading = false,
            productServicesList = productServicesList,
            newProductServiceData = newProductData
        )
    }.onStart {
        ProductServiceUiState(isLoading = true)
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), ProductServiceUiState(
            isLoading = true
        )
    )
    val uiState = _uiState

    private val _events = MutableSharedFlow<String>(extraBufferCapacity = 1)
    val events = _events.asSharedFlow()

    fun addProductService(onSuccess: () -> Unit) {
        viewModelScope.launch {
            if (!isAllData()) {
                _events.emit("Faltan datos!")
            } else {
                val newProductService = ProductService(
                    id = _uiState.value.newProductServiceData.id,
                    label = _uiState.value.newProductServiceData.label,
                    amount = _uiState.value.newProductServiceData.amount
                )
                addProductServicesUseCase(newProductService)
                _events.emit("Añadido correctamente!")
                cleanData()
                onSuccess()
            }
        }
    }

    fun updateLabel(newValue: String) {
        productServiceData.update { current ->
            current.copy(label = newValue)
        }
    }

    fun updateAmount(newValue: String) {

        if (!newValue.isDigitsOnly()) return

        val inLong = if (newValue.isBlank()) 0L else newValue.toLong()

        productServiceData.update { current ->
            current.copy(amount = inLong)
        }
    }

    fun getProductServiceById(id: Int, onDataGet: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            async {
                productServiceData.value = getProductServiceByIdUseCase(id)
            }.await()
            onDataGet()
        }
    }

    private fun isAllData(): Boolean {
        return productServiceData.value.label.isNotBlank() && productServiceData.value.amount > 0L
    }

    fun cleanData() {
        productServiceData.update { current ->
            current.copy(id = 0, label = "", amount = 0L)
        }
    }


}