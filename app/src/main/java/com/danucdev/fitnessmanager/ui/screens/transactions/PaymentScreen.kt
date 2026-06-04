package com.danucdev.fitnessmanager.ui.screens.transactions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.danucdev.fitnessmanager.ui.core.BackIconButton
import com.danucdev.fitnessmanager.ui.core.Header
import com.danucdev.fitnessmanager.ui.theme.DarkAccentLime
import com.danucdev.fitnessmanager.ui.theme.DarkAccentWhite
import com.danucdev.fitnessmanager.ui.theme.MainDark

@Composable
fun PaymentsScreen() {

    var openClientMenu by rememberSaveable { mutableStateOf(false) }
    var clientSelected by rememberSaveable { mutableStateOf("") }
    var clientQuery by rememberSaveable { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Header("Agregar nuevo cobro")
                BackIconButton {
                    //TODO WHEN NAVIGATION IS DONE
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "Agendar nuevo cobro",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    HorizontalDivider(
                        thickness = 1.5.dp,
                        color = DarkAccentLime,
                        modifier = Modifier.padding(horizontal = 68.dp)
                    )
                }
                Spacer(Modifier.size(16.dp))
                ClientSelector(
                    clientSelected = clientSelected,
                    openClientMenu = openClientMenu,
                    clientQuery = clientQuery,
                ) { action, value ->
                    when(action) {
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

            }
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
        "Emmanuel Ramos"
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
            modifier = Modifier.fillMaxWidth()
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
                trailingIcon = { if(clientQuery.isNotBlank()) Icon(Icons.Default.Clear, contentDescription = "borrar nombre", modifier = Modifier.clickable { onActionDone(
                    ClientSelectorActions.MODIFY_QUERY_CLIENT, "") }) }
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