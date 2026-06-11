package com.danucdev.fitnessmanager.ui.screens.clients.clientlist

import com.danucdev.fitnessmanager.domain.models.Client
import kotlinx.coroutines.flow.StateFlow

data class ClientListData(
    val clientsQuantity:Int = 0,
    val clientList: List<Client>,
    val activeClients:Int = 0,
    val inactiveClients:Int = 0
)
