package com.danucdev.fitnessmanager.ui.screens.clients.clientlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danucdev.fitnessmanager.domain.usecases.clients.GetClientsResumeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ClientListViewModel @Inject constructor(getClientsResumeUseCase: GetClientsResumeUseCase): ViewModel() {

    private val _clientData: StateFlow<ClientListData> = getClientsResumeUseCase().stateIn(viewModelScope,
        SharingStarted.WhileSubscribed(5000), ClientListData(
            clientList = emptyList()
        ))
    val clientData = _clientData

}