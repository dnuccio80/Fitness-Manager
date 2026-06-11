package com.danucdev.fitnessmanager.ui.screens.clients.addclients

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danucdev.fitnessmanager.domain.usecases.clients.AddClientUseCase
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
class AddClientsViewModel @Inject constructor(
    private val addClientUseCase: AddClientUseCase
): ViewModel() {

    private val _clientData = MutableStateFlow(ClientData())
    val clientData = _clientData.asStateFlow()

    private val _events = MutableSharedFlow<String>(extraBufferCapacity = 1)
    val events = _events.asSharedFlow()

    fun updateClientName(newValue:String) {
        _clientData.update {current ->
            current.copy(name =  newValue)
        }
    }

    fun updateClientLastname(newValue: String) {
        _clientData.update {current ->
            current.copy(lastName =  newValue)
        }
    }

    fun updateClientPhoneNumber(newValue: String) {
        val inLong = if(newValue.isBlank()) 0 else newValue.toLong()
        _clientData.update {current ->
            current.copy(phoneNumber =  inLong)
        }
    }

    fun toggleClientAlreadyPay() {
        _clientData.update {current ->
            current.copy(alreadyPay = !current.alreadyPay)
        }
    }

    fun addClient() {
        if(isAllData()) {
            viewModelScope.launch(Dispatchers.IO) {
                addClientUseCase(_clientData.value.toClient())
                _events.emit("Cliente guardado con éxito!")
                cleanData()
            }
        } else{
            _clientData.update { current ->
                current.copy(allData = false)
            }
        }
    }

    private fun isAllData(): Boolean {
        return _clientData.value.name.isNotBlank() && _clientData.value.lastName.isNotBlank() && _clientData.value.phoneNumber > 0L
    }

    private fun cleanData() {
        _clientData.update { current ->
            current.copy(name = "", lastName = "", phoneNumber = 0L, alreadyPay = true, allData = true)
        }
    }

}