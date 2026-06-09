package com.danucdev.fitnessmanager.ui.screens.clients.addclients

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danucdev.fitnessmanager.domain.usecases.clients.AddClientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
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
            viewModelScope.launch(Dispatchers.IO) {
                addClientUseCase(_clientData.value.toClient())
                cleanData()
            }
        } else{
            _clientData.update { current ->
                current.copy(allData = false)
            }
        }
    }

    private fun isAllData(): Boolean {
        return _clientData.value.name.isNotBlank() && _clientData.value.lastName.isNotBlank() && _clientData.value.phoneNUmber.isNotBlank()
    }

    private fun cleanData() {
        _clientData.update { current ->
            current.copy(name = "", lastName = "", phoneNUmber = "", alreadyPay = true, allData = true)
        }
    }

}