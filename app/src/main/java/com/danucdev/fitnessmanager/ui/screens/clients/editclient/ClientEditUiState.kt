package com.danucdev.fitnessmanager.ui.screens.clients.editclient

import com.danucdev.fitnessmanager.domain.models.Client

data class ClientEditUiState(
    val isLoading: Boolean = true,
    val client: Client? = null,
    val editForm: DataEditForm = DataEditForm(),
)

data class DataEditForm(
    val name:String = "",
    val lastName:String = "",
    val phone:Long = 0
)
