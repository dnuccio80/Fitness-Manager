package com.danucdev.fitnessmanager.ui.screens.clients.details

import com.danucdev.fitnessmanager.domain.models.Client

data class ClientsDetailsUiState(
    val isLoading: Boolean = true,
    val client: Client? = null
)