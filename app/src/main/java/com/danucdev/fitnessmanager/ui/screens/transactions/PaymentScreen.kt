package com.danucdev.fitnessmanager.ui.screens.transactions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.danucdev.fitnessmanager.ui.core.BackIconButton
import com.danucdev.fitnessmanager.ui.core.MaxWidthButtonLime
import com.danucdev.fitnessmanager.ui.core.NormalHeader
import com.danucdev.fitnessmanager.ui.core.ScreenContainer
import com.danucdev.fitnessmanager.ui.theme.DarkAccentLime
import com.danucdev.fitnessmanager.ui.theme.DarkAccentWhite

@Composable
fun PaymentsScreen(onBack: () -> Unit) {

    var openClientMenu by rememberSaveable { mutableStateOf(false) }
    var clientSelected by rememberSaveable { mutableStateOf("") }
    var clientQuery by rememberSaveable { mutableStateOf("") }

    ScreenContainer("Agregar nuevo cobro", onBack = { onBack() }) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Agendar nuevo cobro",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
            HorizontalDivider(
                thickness = 1.5.dp,
                color = DarkAccentLime,
                modifier = Modifier.padding(horizontal = 75.dp)
            )
        }
        Spacer(Modifier.size(16.dp))
        ClientSelector(
            clientSelected = clientSelected,
            openClientMenu = openClientMenu,
            clientQuery = clientQuery,
        ) { action, value ->
            when (action) {
                ClientSelectorActions.DISMISS -> {
                    openClientMenu = false
                    clientQuery = ""
                }

                ClientSelectorActions.CLIENT_SELECTED -> {
                    openClientMenu = false
                    clientSelected = value.orEmpty()
                    clientQuery = ""
                }

                ClientSelectorActions.OPEN_CLIENT_MENU -> openClientMenu = true
                ClientSelectorActions.MODIFY_QUERY_CLIENT -> clientQuery = value.orEmpty()
            }
        }
        PaymentSelector()
        Row(verticalAlignment = Alignment.CenterVertically) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimaryContainer)
            ) {
                Row(
                    Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Cuota mensual", style = MaterialTheme.typography.labelLarge)
                    Text(
                        "$40.000",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = DarkAccentLime
                    )
                }
            }
            Spacer(Modifier.size(8.dp))
            Icon(
                Icons.Filled.Delete,
                contentDescription = "Eliminar item",
                Modifier
                    .weight(.1f)
                    .clickable {
                        // DELETE ITEM
                    })
        }
        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = { },
                    shape = RoundedCornerShape(8.dp)
                ) { Text("Agregar item") }
                Spacer(Modifier.size(0.dp))
                Text(
                    "Total: $40.000",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = DarkAccentLime
                )
                Spacer(modifier = Modifier.weight(1f))
                MaxWidthButtonLime("Agregar Pago") {
                    // TODO
                }
                Spacer(modifier = Modifier.size(16.dp))
            }
        }
    }
}


@Composable
private fun PaymentSelector() {

    var paymentMethod by rememberSaveable { mutableStateOf("Efectivo") }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Método de pago", style = MaterialTheme.typography.titleMedium)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                paymentMethod = "Efectivo"
            }
        ) {
            RadioButton(
                selected = paymentMethod == "Efectivo",
                onClick = { },
                colors = RadioButtonDefaults.colors(
                    selectedColor = DarkAccentLime,
                    unselectedColor = MaterialTheme.colorScheme.onPrimary
                )
            )
            Text("Efectivo", style = MaterialTheme.typography.labelLarge)
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                paymentMethod = "Transferencia"
            }
        ) {
            RadioButton(
                selected = paymentMethod == "Transferencia",
                onClick = { },
                colors = RadioButtonDefaults.colors(
                    selectedColor = DarkAccentLime,
                    unselectedColor = MaterialTheme.colorScheme.onPrimary
                )
            )
            Text("Transferencia", style = MaterialTheme.typography.labelLarge)
        }
    }
}

@Composable
private fun ClientSelector(
    clientSelected: String,
    openClientMenu: Boolean,
    clientQuery: String,
    onActionDone: (ClientSelectorActions, String?) -> Unit,
) {

    val nameListTest = listOf(
        "Damian Nuccio",
        "Leysa Asnal",
        "Elias Basualdo",
        "Emmanuel Ramos",
        "Emmanuel Ramos",
        "Emmanuel Ramos",
        "Emmanuel Ramos",
        "Emmanuel Ramos",
        "Emmanuel Ramos",
    )

    Column {
        TextField(
            value = if (clientSelected.isEmpty()) "Seleccionar cliente.." else clientSelected,
            onValueChange = {},
            enabled = false,
            trailingIcon = {
                Icon(
                    Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = DarkAccentWhite
                )
            },
            colors = TextFieldDefaults.colors(
                disabledIndicatorColor = Color.Transparent,
                disabledTextColor = DarkAccentWhite
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onActionDone(ClientSelectorActions.OPEN_CLIENT_MENU, null)
                }
        )
        DropdownMenu(
            expanded = openClientMenu,
            onDismissRequest = {
                onActionDone(ClientSelectorActions.DISMISS, null)
            },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 240.dp)
        ) {
            TextField(
                value = clientQuery,
                placeholder = { Text("Buscar por nombre..") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                onValueChange = {
                    onActionDone(ClientSelectorActions.MODIFY_QUERY_CLIENT, it)
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
                maxLines = 1,
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = {
                    if (clientQuery.isNotBlank()) Icon(
                        Icons.Default.Clear,
                        contentDescription = "borrar nombre",
                        modifier = Modifier.clickable {
                            onActionDone(
                                ClientSelectorActions.MODIFY_QUERY_CLIENT, ""
                            )
                        })
                }
            )
            nameListTest.forEach { clientName ->
                DropdownMenuItem(
                    text = { Text(clientName) },
                    onClick = {
                        onActionDone(ClientSelectorActions.CLIENT_SELECTED, clientName)
                    }
                )
            }
        }
    }
}

enum class ClientSelectorActions {
    DISMISS, CLIENT_SELECTED, OPEN_CLIENT_MENU, MODIFY_QUERY_CLIENT
}