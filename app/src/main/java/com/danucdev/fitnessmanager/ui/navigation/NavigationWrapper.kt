package com.danucdev.fitnessmanager.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed class NavRoutes: NavKey {
    @Serializable
    data object Main: NavRoutes()
    @Serializable
    data object Clients: NavRoutes()
    @Serializable
    data object Transactions: NavRoutes()
    @Serializable
    data object Config: NavRoutes()
    @Serializable
    data object Payment: NavRoutes()
    @Serializable
    data object AddClient: NavRoutes()
    @Serializable
    data object Investment: NavRoutes()
    @Serializable
    data object ProductServices: NavRoutes()
    @Serializable
    data class ClientDetails(val clientId:Int): NavRoutes()
    @Serializable
    data class ClientEdit(val clientId:Int): NavRoutes()

}
