package com.danucdev.fitnessmanager.ui.screens.clients.addclients

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.danucdev.fitnessmanager.R
import com.danucdev.fitnessmanager.ui.core.ErrorText
import com.danucdev.fitnessmanager.ui.core.TextFieldForNamesItem
import com.danucdev.fitnessmanager.ui.core.MaxWidthButtonLime
import com.danucdev.fitnessmanager.ui.core.ScreenContainer
import com.danucdev.fitnessmanager.ui.theme.DarkAccentGray
import com.danucdev.fitnessmanager.ui.theme.DarkAccentLime
import com.danucdev.fitnessmanager.ui.theme.MainDark

@Composable
fun AddClientScreen(viewModel: AddClientsViewModel = hiltViewModel(), onBack: () -> Unit) {

    val clientData by viewModel.clientData.collectAsStateWithLifecycle()

    ScreenContainer("Agregar cliente", onBack = { onBack() }) {
        Column(
            Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = DarkAccentLime,
                    contentColor = MainDark
                ), shape = CircleShape
            ) {
                Icon(
                    painterResource(R.drawable.ic_person),
                    contentDescription = null,
                    modifier = Modifier
                        .size(128.dp)
                        .padding(16.dp)
                )
            }
            Text(
                "Nuevo Cliente",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineSmall
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            TextFieldForNamesItem(
                value = clientData.name,
                placeholder = "Nombres"
            ) { viewModel.updateClientName(it) }
            TextFieldForNamesItem(
                value = clientData.lastName,
                placeholder = "Apellido"
            ) { viewModel.updateClientLastname(it) }
            TextFieldForNamesItem(
                value = clientData.phoneNUmber,
                placeholder = "Teléfono",
                numberOnly = true
            ) { viewModel.updateClientPhoneNumber(it) }
            CheckLabelItem("Ya pagó la cuota", clientData.alreadyPay) { viewModel.toggleClientAlreadyPay() }
            AnimatedVisibility(!clientData.allData) {
                ErrorText("Faltan rellenar datos!")
            }
            Spacer(modifier = Modifier.weight(1f))
            MaxWidthButtonLime("Agregar cliente") {
                viewModel.addClient()
            }
            Spacer(modifier = Modifier.size(16.dp))
        }

    }
}


@Composable
fun CheckLabelItem(label: String, checked: Boolean, onCheckedChange: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable {
            onCheckedChange()
        }
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = { onCheckedChange() },
            colors = CheckboxDefaults.colors(
                checkedColor = DarkAccentLime,
                uncheckedColor = DarkAccentGray,
                checkmarkColor = MainDark
            )
        )
        Text(
            label,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}
