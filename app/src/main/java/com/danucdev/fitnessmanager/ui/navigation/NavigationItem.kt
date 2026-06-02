package com.danucdev.fitnessmanager.ui.navigation

import com.danucdev.fitnessmanager.R

sealed class BottomNavigationItem(val label: String,val icon: Int) {
    data object Clients: BottomNavigationItem("Clientes", R.drawable.ic_person)
    data object Transactions: BottomNavigationItem("Transacciones", R.drawable.ic_wallet)
    data object Config: BottomNavigationItem("Configuración", R.drawable.ic_config)
}
