package com.danucdev.fitnessmanager.ui.screens.clients.clientlist

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import com.danucdev.fitnessmanager.R
import com.danucdev.fitnessmanager.domain.models.Client
import com.danucdev.fitnessmanager.ui.core.NormalHeader
import com.danucdev.fitnessmanager.ui.screens.main.BottomBar
import com.danucdev.fitnessmanager.ui.theme.DarkAccentLime
import com.danucdev.fitnessmanager.ui.theme.MainDark

@Composable
fun ClientsScreen(
    viewModel: ClientListViewModel = hiltViewModel(),
    currentRoute: NavKey,
    onBottomBarClick: (NavKey) -> Unit,
    onNavigateToDetails: (Int)-> Unit
) {

    val clientData = viewModel.clientData.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = { BottomBar(currentRoute) { route -> onBottomBarClick(route) } }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                NormalHeader("Listado de clientes")
                Spacer(Modifier.size(0.dp))
                SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                    SegmentedButton(
                        selected = false,
                        onClick = { },
                        shape = SegmentedButtonDefaults.itemShape(0, 3),
                        label = { Text("Activos") }
                    )
                    SegmentedButton(
                        selected = false,
                        onClick = { },
                        shape = SegmentedButtonDefaults.itemShape(1, 3),
                        label = { Text("Cuota vencida") }
                    )
                    SegmentedButton(
                        selected = true,
                        onClick = { },
                        shape = SegmentedButtonDefaults.itemShape(2, 3),
                        label = { Text("Ver todos") }
                    )

                }

                LazyColumn(
                    Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            val clientsPaid = clientData.value.activeClients
                            Text(
                                "Ya pagaron el mes ($clientsPaid)",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleMedium
                            )
                            HorizontalDivider(
                                Modifier.fillMaxWidth(),
                                thickness = 1.dp,
                                color = DarkAccentLime
                            )
                        }
                    }
                    if (clientData.value.clientList.isEmpty()) {
                        item {
                            Text(
                                "No hay clientes que hayan pagado este mes",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    } else {
                        items(clientData.value.clientList) { client ->
                            ActiveClientRow(client) { onNavigateToDetails(client.clientId)  }
                        }
                    }
                    item {
                        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            Text(
                                "Pendientes de pago (20)",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleMedium
                            )
                            HorizontalDivider(
                                Modifier.fillMaxWidth(),
                                thickness = 1.dp,
                                color = DarkAccentLime
                            )
                        }
                    }
                    items(10) {
                        InactiveClient(
                            Client(
                                name = "Mateo",
                                lastName = "Fernandez",
                                phone = 3571596202
                            )
                        )
                    }
                    item {
                        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            Text(
                                "Inactivos (80)",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleMedium
                            )
                            HorizontalDivider(
                                Modifier.fillMaxWidth(),
                                thickness = 1.dp,
                                color = DarkAccentLime
                            )
                        }
                    }
                    items(10) {
                        InactiveClient(
                            Client(
                                name = "Mateo",
                                phone = 2136456,
                                lastName = "Fernandez"
                            )
                        )
                    }
                }
            }


        }
    }
}

@Composable
private fun ActiveClientRow(client: Client, onClick: () -> Unit) {
    Card(
        Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Card(
                shape = CircleShape,
                colors = CardDefaults.cardColors(
                    containerColor = DarkAccentLime,
                    contentColor = MainDark
                )
            ) {
                Text(
                    "${client.name.first()}${client.lastName.first()}",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(4.dp)
                )
            }
            Text(
                "${client.name} ${client.lastName}",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Medium
            )
            Spacer(Modifier.weight(1f))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    painterResource(R.drawable.ic_timer),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.surfaceVariant
                )
                Text(
                    "31 días",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(4.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant
                )
            }
        }
    }
}

@Composable
private fun InactiveClient(client: Client) {
    Card(
        Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Card(
                shape = CircleShape,
                colors = CardDefaults.cardColors(
                    containerColor = DarkAccentLime,
                    contentColor = MainDark
                )
            ) {
                Text(
                    "${client.name.first()}${client.lastName.first()}",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(4.dp)
                )
            }
            Text(
                "${client.name} ${client.lastName}",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Medium
            )

        }
    }
}