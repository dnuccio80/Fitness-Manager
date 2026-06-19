package com.danucdev.fitnessmanager.ui.screens.clients.details

import android.widget.Toast
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.danucdev.fitnessmanager.domain.models.Client
import com.danucdev.fitnessmanager.ui.core.ConfirmDialog
import com.danucdev.fitnessmanager.ui.core.ScreenContainer
import com.danucdev.fitnessmanager.ui.navigation.BottomClientDetailsNavigationItem
import com.danucdev.fitnessmanager.ui.theme.AccentColor2
import com.danucdev.fitnessmanager.ui.theme.DarkAccentLime
import com.danucdev.fitnessmanager.ui.theme.ErrorContainer
import com.danucdev.fitnessmanager.ui.theme.MainDark

@Composable
fun ClientDetailsScreen(
    clientId: Int,
    viewModel: ClientDetailsViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onEdit: () -> Unit,
) {

    var showDialog by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.events) {
        viewModel.events.collect { msg ->
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(clientId) {
        viewModel.setClientId(clientId)
    }

    ScreenContainer(
        headerLabel = "Detalles de cliente",
        onBack = { onBack() }
    ) {
        if (uiState.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = DarkAccentLime)
            }
        } else {
            ContentData(
                client = uiState.client,
                onEdit = { onEdit() },
                onDelete = { showDialog = true }
            )
        }
        if (showDialog) {
            ConfirmDialog(
                text = "Seguro que queres eliminar el cliente?",
                onConfirm = {
                    showDialog = false
                    viewModel.deleteClient { onBack() }
                },
                onDismiss = { showDialog = false }
            )
        }
    }

}

@Composable
private fun ContentData(client: Client?, onEdit: () -> Unit, onDelete: () -> Unit) {

    if (client == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No se ha encontrado información acerca del cliente")
        }
    } else {

        val shortName = "${client.name.first()}${client.lastName.first()}"
        val longName = "${client.name} ${client.lastName}"

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
                    shortName,
                    fontWeight = FontWeight.ExtraBold,
                    style = MaterialTheme.typography.displayLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }
            Text(
                longName,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineSmall
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            DetailsRowItem("Nombres:", client.name)
            DetailsRowItem("Apellidos:", client.lastName)
            DetailsRowItem("Teléfono:", client.phone.toString())
            DetailsRowItem("Último pago:", "26-06-2026")
            Spacer(Modifier.size(8.dp))
            Column(
                Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { onEdit() },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AccentColor2)
                ) {
                    Text("Editar datos", style = MaterialTheme.typography.bodyLarge)
                }
                Button(
                    onClick = { onDelete() },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = ErrorContainer)
                ) {
                    Text("Eliminar cliente", style = MaterialTheme.typography.bodyLarge)
                }
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

