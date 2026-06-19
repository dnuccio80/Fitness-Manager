package com.danucdev.fitnessmanager.ui.screens.clients.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danucdev.fitnessmanager.domain.usecases.clients.DeleteClientByIdUseCase
import com.danucdev.fitnessmanager.domain.usecases.clients.GetClientByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class ClientDetailsViewModel @Inject constructor(
    getClientByIdUseCase: GetClientByIdUseCase,
    private val deleteClientByIdUseCase: DeleteClientByIdUseCase
) : ViewModel() {

    private val _clientId = MutableStateFlow<Int?>(null)

    private val _events = MutableSharedFlow<String>(extraBufferCapacity = 1)
    val events: SharedFlow<String> = _events

    val uiState: StateFlow<ClientsDetailsUiState> = _clientId
        .filterNotNull()
        .flatMapLatest { id ->
            getClientByIdUseCase(id).map { client ->
                ClientsDetailsUiState(false, client)
            }
                .onStart {
                    emit(ClientsDetailsUiState(false))
                }
                .catch { e ->
                    emit(ClientsDetailsUiState(false))
                    handleError(e)
                }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ClientsDetailsUiState(true))

    private suspend fun handleError(error: Throwable) {
        _events.emit(error.message.orEmpty())
    }

    fun setClientId(newValue:Int) {
        _clientId.value = newValue
    }

    fun deleteClient(onActionDone:() -> Unit) {
        viewModelScope.launch {

            if(_clientId.value == null) return@launch

            deleteClientByIdUseCase(_clientId.value ?: 0)
            _events.emit("Cliente eliminado correctamente!")
            onActionDone()
        }
    }

}