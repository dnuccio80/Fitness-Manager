package com.danucdev.fitnessmanager.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.ui.graphics.vector.ImageVector
import com.danucdev.fitnessmanager.R

sealed class BottomNavigationItem(val label: String,val icon: Int) {
    data object Clients: BottomNavigationItem("Clientes", R.drawable.ic_person)
    data object Transactions: BottomNavigationItem("Transacciones", R.drawable.ic_wallet)
    data object Config: BottomNavigationItem("Configuración", R.drawable.ic_config)
}

sealed class BottomClientDetailsNavigationItem(val label:String, val icon: ImageVector) {
    data object Edit: BottomClientDetailsNavigationItem("Editar", Icons.Default.Edit)
    data object Message: BottomClientDetailsNavigationItem("WhatsApp", Icons.Default.Call)
    data object Delete: BottomClientDetailsNavigationItem("Eliminar", Icons.Default.Delete)
}