package com.danucdev.fitnessmanager.ui.screens.productservices

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.window.Dialog
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.danucdev.fitnessmanager.ui.core.AcceptDeclineButtonItem
import com.danucdev.fitnessmanager.ui.core.DescriptionCardWithPrice
import com.danucdev.fitnessmanager.ui.core.ScreenContainer
import com.danucdev.fitnessmanager.ui.core.TextFieldForSentencesItem
import com.danucdev.fitnessmanager.ui.theme.CardDark
import com.danucdev.fitnessmanager.ui.theme.DarkAccentLime

@Composable
fun ProductServicesScreen(
    viewModel: ProductServicesViewModel = hiltViewModel(),
    onBack: () -> Unit,
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val amount =
        if (uiState.newProductServiceData.amount == 0L) "" else uiState.newProductServiceData.amount.toString()
    var showNewProductServiceDialog by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(viewModel.events) {
        viewModel.events.collect { msg ->
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
    }

    ScreenContainer(
        "Productos y servicios",
        onBack = { onBack() }
    ) {
        Column {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Productos y servicios", style = MaterialTheme.typography.titleLarge)
                Button(
                    onClick = { showNewProductServiceDialog = true },
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CardDark
                    )
                ) {
                    Text(
                        "Agregar",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            HorizontalDivider(Modifier.fillMaxWidth(), thickness = 1.dp, DarkAccentLime)
        }
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            if (uiState.productServicesList.isNullOrEmpty()) {
                item {
                    Text(
                        "No hay productos o servicios agregados todavía",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else {
                items(uiState.productServicesList!!) { item ->
                    DescriptionCardWithPrice(
                        title = item.label,
                        amount = item.amount
                    ) {
                        viewModel.getProductServiceById(item.id) {
                            showNewProductServiceDialog = true
                        }
                    }
                }
            }
        }
        if (showNewProductServiceDialog) {
            NewProductServiceDialog(
                label = uiState.newProductServiceData.label,
                amount = amount,
                onDismiss = {
                    showNewProductServiceDialog = false
                    viewModel.cleanData()
                },
                onConfirm = {
                    viewModel.addProductService { showNewProductServiceDialog = false }
                },
                onLabelChange = { viewModel.updateLabel(it) },
                onAmountChange = { viewModel.updateAmount(it) },
            )
        }

    }

}

@Composable
private fun NewProductServiceDialog(
    label: String,
    amount: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    onLabelChange: (String) -> Unit,
    onAmountChange: (String) -> Unit,

    ) {
    Dialog(
        onDismissRequest = { onDismiss() }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(4.dp),
            colors = CardDefaults.cardColors(
                containerColor = CardDark
            )
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                TextFieldForSentencesItem(
                    value = label,
                    placeholder = "Descripción",
                ) { onLabelChange(it) }
                TextFieldForSentencesItem(
                    value = amount,
                    placeholder = "Monto",
                    numberOnly = true,
                ) { onAmountChange(it) }
                Spacer(Modifier.height(16.dp))
                Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    AcceptDeclineButtonItem(
                        onConfirm = { onConfirm() },
                        onDismiss = { onDismiss() }
                    )
                }
            }
        }
    }
}