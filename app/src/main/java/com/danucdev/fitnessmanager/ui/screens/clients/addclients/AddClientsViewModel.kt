package com.danucdev.fitnessmanager.ui.screens.clients.addclients

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AddClientsViewModel @Inject constructor(): ViewModel() {

    private val _clientData = MutableStateFlow(ClientData())
    val clientData = _clientData.asStateFlow()

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
        _clientData.update {current ->
            current.copy(phoneNUmber =  newValue)
        }
    }

    fun toggleClientAlreadyPay() {
        _clientData.update {current ->
            current.copy(alreadyPay = !current.alreadyPay)
        }
    }

    fun addClient() {
        if(isAllData()) {
            // ADD CLIENT
            _clientData.update { current ->
                current.copy(allData = true)
            }
        } else{
            _clientData.update { current ->
                current.copy(allData = false)
            }
        }
    }

    fun isAllData(): Boolean {
        return _clientData.value.name.isNotBlank() && _clientData.value.lastName.isNotBlank() && _clientData.value.phoneNUmber.isNotBlank()
    }
}