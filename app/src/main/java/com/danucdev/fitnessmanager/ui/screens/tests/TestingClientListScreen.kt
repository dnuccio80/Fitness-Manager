package com.danucdev.fitnessmanager.ui.screens.tests

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.danucdev.fitnessmanager.ui.core.ScreenContainer


@Composable
fun TestingClientsScreen(viewModel: TestingViewModel = hiltViewModel(), onBack:() -> Unit) {

    val clients by viewModel.clients.collectAsStateWithLifecycle()

    ScreenContainer(
        headerLabel = "Testing Screen",
        onBack = { onBack() }
    ) {
        if(clients.isNotEmpty()) {
            clients.forEach { client ->
                Card {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(client.name)
                        Text(client.phone)
                    }
                }
            }
        } else {
            Text("La lista esta empty")
        }
    }
}