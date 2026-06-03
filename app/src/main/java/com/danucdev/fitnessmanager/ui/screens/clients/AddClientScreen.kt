package com.danucdev.fitnessmanager.ui.screens.clients

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.Scaffold
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
import com.danucdev.fitnessmanager.R
import com.danucdev.fitnessmanager.ui.core.AcceptDeclineButtonItem
import com.danucdev.fitnessmanager.ui.core.BackIconButton
import com.danucdev.fitnessmanager.ui.core.EditClientInfoRowItem
import com.danucdev.fitnessmanager.ui.core.Header
import com.danucdev.fitnessmanager.ui.theme.DarkAccentGray
import com.danucdev.fitnessmanager.ui.theme.DarkAccentLime
import com.danucdev.fitnessmanager.ui.theme.MainDark

@Composable
fun AddClientScreen() {

    var clientName by rememberSaveable { mutableStateOf("") }
    var clientLastname by rememberSaveable { mutableStateOf("") }
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    var alreadyPay by rememberSaveable { mutableStateOf(true) }

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
                Header("Agregar cliente")
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
                CheckLabelItem("Ya pagó la cuota", alreadyPay) { alreadyPay = !alreadyPay }
                Spacer(modifier = Modifier.size(32.dp))
                Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    AcceptDeclineButtonItem(acceptLabel = "Guardar", onConfirm = {}, onDismiss = {})
                }
            }
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
        Text(label, style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onPrimary)
    }
}
