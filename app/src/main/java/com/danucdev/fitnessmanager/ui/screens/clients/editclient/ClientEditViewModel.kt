package com.danucdev.fitnessmanager.ui.screens.clients.editclient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danucdev.fitnessmanager.domain.models.Client
import com.danucdev.fitnessmanager.domain.usecases.clients.GetClientByIdUseCase
import com.danucdev.fitnessmanager.domain.usecases.clients.UpdateClientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ClientEditViewModel @Inject constructor(
    private val getClientByIdUseCase: GetClientByIdUseCase,
    private val updateClientUseCase: UpdateClientUseCase
) :
    ViewModel() {

    private val clientId = MutableStateFlow<Int?>(null)
    private val _editForm = MutableStateFlow<DataEditForm>(DataEditForm())
    private val _client = MutableStateFlow<Client?>(null)

    private val _events = MutableSharedFlow<String>(extraBufferCapacity = 1)
    val events: SharedFlow<String> = _events

    private val _uiState = combine(
        _client,
        _editForm
    ) { client, editForm ->

        ClientEditUiState(
            isLoading = false,
            client = client,
            editForm = editForm
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ClientEditUiState(true))
    val uiState = _uiState


    fun setClientId(newValue: Int) {
        clientId.value = newValue

        viewModelScope.launch {
            getClientByIdUseCase(newValue).first().let {client ->
                _client.value = client

                _editForm.value = DataEditForm(
                    name = client.name,
                    lastName = client.lastName,
                    phone = client.phone
                )
            }
        }

    }

    private suspend fun handleError(error: Throwable) {
        _events.emit(error.message.orEmpty())
    }

    fun updateName(newValue: String) {
        _editForm.update { current ->
            current.copy(name = newValue)
        }
    }

    fun updateLastname(newValue: String) {
        _editForm.update { current ->
            current.copy(lastName = newValue)
        }
    }

    fun updateClient(onUpdatedData:() -> Unit) {
        viewModelScope.launch {

            if(!checkData()) {
                _events.emit("Falta rellenar datos!")
                return@launch
            }

            val updatedData = Client(
                clientId = _client.value?.clientId ?: 0,
                name = _editForm.value.name,
                lastName = _editForm.value.lastName,
                phone = _editForm.value.phone
            )

            updateClientUseCase(updatedData)
            _events.emit("Datos actualizados correctamente!")
            onUpdatedData()
        }
    }

    fun updatePhone(newValue: String) {
        val inLong = if (newValue.isNotBlank()) newValue.toLong() else 0
        _editForm.update { current ->
            current.copy(phone = inLong)
        }
    }

    private fun checkData(): Boolean {
        return _editForm.value.name.isNotBlank() && _editForm.value.lastName.isNotBlank() && _editForm.value.phone > 0
    }

}