package com.danucdev.fitnessmanager.ui.screens.clients.editclient

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.danucdev.fitnessmanager.domain.models.Client
import com.danucdev.fitnessmanager.ui.core.ConfirmDialog
import com.danucdev.fitnessmanager.ui.core.ScreenContainer
import com.danucdev.fitnessmanager.ui.core.TextFieldForNamesItem
import com.danucdev.fitnessmanager.ui.screens.clients.editclient.EditClientDataActions.*
import com.danucdev.fitnessmanager.ui.theme.DarkAccentLime
import com.danucdev.fitnessmanager.ui.theme.MainDark

@Composable
fun ClientEditScreen(
    clientId: Int,
    viewModel: ClientEditViewModel = hiltViewModel(),
    onBack: () -> Unit,
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showDialog by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(clientId) {
        viewModel.setClientId(clientId)
    }

    LaunchedEffect(viewModel.events) {
        viewModel.events.collect { e ->
            Toast.makeText(context, e, Toast.LENGTH_SHORT).show()
        }
    }

    ScreenContainer(
        headerLabel = "Editar cliente",
        onBack = { onBack() }
    ) {
        if (uiState.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = DarkAccentLime)
            }
        } else {
            ContentData(
                client = uiState.client,
                form = uiState.editForm,
                onActionDone = { action, value ->
                    when (action) {
                        EDIT_NAME -> { viewModel.updateName(value) }
                        EDIT_LASTNAME -> { viewModel.updateLastname(value) }
                        EDIT_PHONE -> { viewModel.updatePhone(value) }
                    }
                },
                onUpdate = { showDialog = true },
            )
        }
        if (showDialog) {
            ConfirmDialog(
                text = "Seguro que queres actualizar los datos del cliente?",
                onConfirm = {
                    showDialog = false
                    viewModel.updateClient { onBack() }
                },
                onDismiss = { showDialog = false }
            )
        }
    }
}

enum class EditClientDataActions {
    EDIT_NAME, EDIT_LASTNAME, EDIT_PHONE
}


@Composable
private fun ContentData(
    client: Client?,
    form: DataEditForm,
    onActionDone: (EditClientDataActions, String) -> Unit,
    onUpdate: () -> Unit,
) {

    if(client == null) return

    val shortName = "${client.name.first()}${client.lastName.first()}"
    val longName = "${client.name} ${client.lastName}"
    val phone = if(form.phone > 0) form.phone.toString() else ""

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
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            TextFieldForNamesItem(
                value = form.name,
                placeholder = "Nombres"
            ) { onActionDone(EDIT_NAME, it) }
            TextFieldForNamesItem(
                value = form.lastName,
                placeholder = "Apellido"
            ) { onActionDone(EDIT_LASTNAME, it) }
            TextFieldForNamesItem(
                value = phone,
                placeholder = "Teléfono",
                numberOnly = true
            ) { onActionDone(EDIT_PHONE, it) }
        }
        Spacer(Modifier.weight(1f))
        Button(
            onClick = { onUpdate() },
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DarkAccentLime)
        ) {
            Text("Actualizar datos", style = MaterialTheme.typography.bodyLarge, color = MainDark, fontWeight = FontWeight.Bold)
        }
    }
}
