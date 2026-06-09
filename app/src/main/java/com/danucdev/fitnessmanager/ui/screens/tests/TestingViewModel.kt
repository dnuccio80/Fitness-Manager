package com.danucdev.fitnessmanager.ui.screens.tests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danucdev.fitnessmanager.domain.models.Client
import com.danucdev.fitnessmanager.domain.repositories.ClientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TestingViewModel @Inject constructor(clientRepository: ClientRepository): ViewModel() {

    private val _clients: StateFlow<List<Client>> = clientRepository.getAllClients().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val clients = _clients

}