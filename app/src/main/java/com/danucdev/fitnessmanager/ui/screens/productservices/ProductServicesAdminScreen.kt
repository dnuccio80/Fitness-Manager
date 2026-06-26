package com.danucdev.fitnessmanager.ui.screens.productservices

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.danucdev.fitnessmanager.ui.core.DescriptionCardWithPrice
import com.danucdev.fitnessmanager.ui.core.ScreenContainer
import com.danucdev.fitnessmanager.ui.core.TitleWithDivider

@Composable
fun ProductServicesScreen(onBack:() -> Unit) {

    ScreenContainer(
        "Productos y servicios",
        onBack = { onBack() }
    ) {
        TitleWithDivider("Productos y servicios")
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(5) {
                DescriptionCardWithPrice("Cuota mensual", "40000")
            }
        }
    }

}