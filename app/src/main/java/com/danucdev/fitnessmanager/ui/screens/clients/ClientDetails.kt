package com.danucdev.fitnessmanager.ui.screens.clients

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.danucdev.fitnessmanager.ui.core.BackIconButton
import com.danucdev.fitnessmanager.ui.core.ConfirmDialog
import com.danucdev.fitnessmanager.ui.core.Header
import com.danucdev.fitnessmanager.ui.navigation.BottomClientDetailsNavigationItem
import com.danucdev.fitnessmanager.ui.theme.DarkAccentLime
import com.danucdev.fitnessmanager.ui.theme.MainDark

@Composable
fun ClientDetailsScreen() {

    var showDialog by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = { ClientsBottomBar { showDialog = true } }
    ) { innerPadding ->


        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Header("Información de cliente")
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
                    DetailsRowItem("Nombres:", "Damian Nicolás")
                    DetailsRowItem("Apellidos:", "Nuccio")
                    DetailsRowItem("Teléfono:", "3571590020")
                    DetailsRowItem("Último pago:", "26-06-2026")
                }
            }
            if (showDialog) {
                ConfirmDialog(
                    text = "Seguro que queres eliminar el cliente?",
                    onConfirm = { showDialog = false },
                    onDismiss = { showDialog = false }
                )
            }

        }
    }
}

@Composable
private fun DetailsRowItem(label: String, description: String) {
    Card(
        Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(label, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text(
                description,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

@Composable
private fun ClientsBottomBar(onClick: () -> Unit) {

    val navItemList = listOf(
        BottomClientDetailsNavigationItem.Edit,
        BottomClientDetailsNavigationItem.Message,
        BottomClientDetailsNavigationItem.Delete
    )

    NavigationBar(containerColor = Color.Transparent) {
        navItemList.forEach {
            NavigationBarItem(
                selected = false,
                onClick = { onClick() },
                icon = { Icon(it.icon, contentDescription = null) },
                label = { Text(it.label) }
            )
        }
    }
}

