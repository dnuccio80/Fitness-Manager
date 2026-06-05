package com.danucdev.fitnessmanager.ui.screens.clients

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.danucdev.fitnessmanager.R
import com.danucdev.fitnessmanager.ui.core.AcceptDeclineButtonItem
import com.danucdev.fitnessmanager.ui.core.BackIconButton
import com.danucdev.fitnessmanager.ui.core.TextFieldForNamesItem
import com.danucdev.fitnessmanager.ui.core.Header
import com.danucdev.fitnessmanager.ui.theme.DarkAccentGray
import com.danucdev.fitnessmanager.ui.theme.DarkAccentLime
import com.danucdev.fitnessmanager.ui.theme.DarkAccentWhite
import com.danucdev.fitnessmanager.ui.theme.MainDark

@Composable
fun AddClientScreen() {

    var clientName by rememberSaveable { mutableStateOf("") }
    var clientLastname by rememberSaveable { mutableStateOf("") }
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    var alreadyPay by rememberSaveable { mutableStateOf(true) }
    var showDropdownMenuQuantity by rememberSaveable { mutableStateOf(false) }
    var monthlyQuantityPaid by rememberSaveable { mutableIntStateOf(1) }

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
                    TextFieldForNamesItem(
                        value = clientName,
                        placeholder = "Nombres"
                    ) { clientName = it }
                    TextFieldForNamesItem(
                        value = clientLastname,
                        placeholder = "Apellido"
                    ) { clientLastname = it }
                    TextFieldForNamesItem(
                        value = phoneNumber,
                        placeholder = "Teléfono",
                        numberOnly = true
                    ) { phoneNumber = it }
                }
                CheckLabelItem("Ya pagó la cuota", alreadyPay) { alreadyPay = !alreadyPay }
                AnimatedVisibility(alreadyPay) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            "Cantidad de cuotas pagas:",
                            style = MaterialTheme.typography.titleSmall
                        )
                        Column {
                            TextField(
                                value = monthlyQuantityPaid.toString(), onValueChange = {}, enabled = false, trailingIcon = {
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
                                shape = RoundedCornerShape(4.dp),
                                modifier = Modifier
                                    .width(80.dp)
                                    .clickable {
                                        showDropdownMenuQuantity = true
                                    }
                            )
                            DropdownMenu(
                                expanded = showDropdownMenuQuantity,
                                onDismissRequest = { showDropdownMenuQuantity = false },
                                modifier = Modifier.height(150.dp)
                            ) {
                                (1..10).forEach {
                                    DropdownMenuItem(
                                        text = { Text(it.toString()) },
                                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
                                        onClick = {
                                            showDropdownMenuQuantity = false
                                            monthlyQuantityPaid = it
                                        }
                                    )
                                }
                            }
                        }


                    }
                }
                Spacer(modifier = Modifier.size(16.dp))
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
        Text(
            label,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}
