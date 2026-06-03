package com.danucdev.fitnessmanager.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.danucdev.fitnessmanager.ui.core.AcceptDeclineButtonItem
import com.danucdev.fitnessmanager.ui.core.BackIconButton
import com.danucdev.fitnessmanager.ui.core.ConfirmDialog
import com.danucdev.fitnessmanager.ui.core.Header
import com.danucdev.fitnessmanager.ui.theme.DarkAccentLime
import com.danucdev.fitnessmanager.ui.theme.MainDark

@Composable
fun ClientEditScreen() {

    var clientName by rememberSaveable { mutableStateOf("") }
    var clientLastname by rememberSaveable { mutableStateOf("") }
    var phoneNumber by rememberSaveable { mutableStateOf("") }

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
                Header("Editar cliente")
                BackIconButton {
                    //TODO WHEN NAVIGATION IS DONE
                }
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
                        Text(
                            "DN",
                            fontWeight = FontWeight.ExtraBold,
                            style = MaterialTheme.typography.displayLarge,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    Text(
                        "Damian Nuccio",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    EditClientInfoRowItem(
                        value = clientName,
                        placeholder = "Nombres"
                    ) { clientName = it }
                    EditClientInfoRowItem(
                        value = clientLastname,
                        placeholder = "Apellido"
                    ) { clientLastname = it }
                    EditClientInfoRowItem(
                        value = phoneNumber,
                        placeholder = "Teléfono",
                        numberOnly = true
                    ) { phoneNumber = it }
                }
                Spacer(modifier = Modifier.size(32.dp))
                Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    AcceptDeclineButtonItem( onConfirm = {}, onDismiss = {})
                }
            }
        }
    }
}

@Composable
private fun EditClientInfoRowItem(
    value: String,
    placeholder: String,
    numberOnly: Boolean = false,
    onValueChange: (String) -> Unit,
) {
    TextField(
        value = if(numberOnly) value.filter { it.isDigit() } else value,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(placeholder) },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent
        ),
        keyboardOptions = if(numberOnly)KeyboardOptions(keyboardType = KeyboardType.Number) else KeyboardOptions(capitalization = KeyboardCapitalization.Words),
        singleLine = true,
        maxLines = 1,
        onValueChange = { onValueChange(it) })
}