package com.danucdev.fitnessmanager.ui.screens.clients

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.danucdev.fitnessmanager.ui.core.Header
import com.danucdev.fitnessmanager.ui.theme.DarkAccentLime
import com.danucdev.fitnessmanager.ui.theme.DarkButton
import com.danucdev.fitnessmanager.ui.theme.MainDark

@Composable
fun ClientsScreen() {
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
            LazyColumn(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                item { Header("Listado de clientes") }
                item {
                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = {},
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = DarkButton
                            )
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "",
                                    modifier = Modifier.size(18.dp)
                                )
                                Text("Volver", style = MaterialTheme.typography.labelMedium)
                            }
                        }
                        Button(
                            onClick = {},
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = DarkButton
                            )
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text("Ver todos", style = MaterialTheme.typography.labelMedium)
                                Icon(
                                    Icons.Filled.KeyboardArrowDown,
                                    contentDescription = "",
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text("Ya pagaron el mes (100)", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                        HorizontalDivider(Modifier.fillMaxWidth(), thickness = 1.dp, color = DarkAccentLime)
                    }
                }
                items(10) {
                    ClientRowItem()
                }
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text("Pendientes de pago (20)", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                        HorizontalDivider(Modifier.fillMaxWidth(), thickness = 1.dp, color = DarkAccentLime)
                    }
                }
                items(10) {
                    ClientRowItem()
                }
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text("Inactivos (80)", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                        HorizontalDivider(Modifier.fillMaxWidth(), thickness = 1.dp, color = DarkAccentLime)
                    }
                }
                items(10) {
                    ClientRowItem()
                }
            }
        }
    }
}

@Composable
private fun ClientRowItem() {
    Card(
        Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Card(shape = CircleShape, colors = CardDefaults.cardColors(containerColor = DarkAccentLime, contentColor = MainDark)) {
                Text("DN", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, modifier = Modifier.padding(4.dp))
            }
            Text(
                "Damian Nuccio",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Medium
            )
        }
    }
}