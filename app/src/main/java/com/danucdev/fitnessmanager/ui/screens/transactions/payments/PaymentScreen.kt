package com.danucdev.fitnessmanager.ui.screens.transactions.payments

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.danucdev.fitnessmanager.domain.models.Client
import com.danucdev.fitnessmanager.domain.models.ProductService
import com.danucdev.fitnessmanager.ui.core.MaxWidthButtonLime
import com.danucdev.fitnessmanager.ui.core.ScreenContainer
import com.danucdev.fitnessmanager.ui.core.TitleWithDivider
import com.danucdev.fitnessmanager.ui.core.ex.toPrice
import com.danucdev.fitnessmanager.ui.screens.transactions.payments.PaymentMethod.CASH
import com.danucdev.fitnessmanager.ui.screens.transactions.payments.PaymentMethod.TRANSFER
import com.danucdev.fitnessmanager.ui.theme.CardDark
import com.danucdev.fitnessmanager.ui.theme.DarkAccentLime
import com.danucdev.fitnessmanager.ui.theme.DarkAccentWhite
import kotlin.math.roundToInt

enum class PaymentMethod(val method: String) {
    CASH("Efectivo"), TRANSFER("Transferencia")
}

@Composable
fun PaymentsScreen(viewModel: PaymentsViewModel = hiltViewModel(), onBack: () -> Unit) {

    var openClientMenu by rememberSaveable { mutableStateOf(false) }
    var clientQuery by rememberSaveable { mutableStateOf("") }
    var showItemList by rememberSaveable { mutableStateOf(false) }

    val context = LocalContext.current
    val clients by viewModel.clientsList.collectAsStateWithLifecycle()
    val paymentData by viewModel.paymentData.collectAsStateWithLifecycle()
    val productServicesList by viewModel.productServices.collectAsStateWithLifecycle()
    val productServicesChart by viewModel.productServicesChart.collectAsStateWithLifecycle()
    val productServicesTotal by viewModel.productServicesTotal.collectAsStateWithLifecycle()



    LaunchedEffect(viewModel.events) {
        viewModel.events.collect { msg ->
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
    }

    ScreenContainer("Agregar nuevo cobro", onBack = { onBack() }) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Agendar nuevo cobro",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
            HorizontalDivider(
                thickness = 1.5.dp,
                color = DarkAccentLime,
                modifier = Modifier.padding(horizontal = 75.dp)
            )
        }
        Spacer(Modifier.size(16.dp))
        ClientSelector(
            clientSelected = paymentData.clientName,
            openClientMenu = openClientMenu,
            clients,
            clientQuery = clientQuery,
        ) { action, value ->
            when (action) {
                ClientSelectorActions.DISMISS -> {
                    openClientMenu = false
                    clientQuery = ""
                }

                ClientSelectorActions.CLIENT_SELECTED -> {
                    openClientMenu = false
                    viewModel.updateClientName(value.orEmpty())
                    clientQuery = ""
                }

                ClientSelectorActions.OPEN_CLIENT_MENU -> openClientMenu = true
                ClientSelectorActions.MODIFY_QUERY_CLIENT -> clientQuery = value.orEmpty()
            }
        }
        PaymentSelector(
            paymentSelected = paymentData.paymentMethod,
            onPaymentSelected = { viewModel.updatePaymentMethod(it) }
        )
        Column {
            Text(
                "Items a cobrar",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            HorizontalDivider(thickness = 1.dp, color = DarkAccentLime)
        }
        LazyColumn(modifier = Modifier.heightIn(max = 190.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            if (productServicesChart.isNotEmpty()) {
                productServicesChart.forEach { productService ->
                    item { DraggableCardItem(productService) { viewModel.deleteItemFromChart(productService) } }
                }
            } else {
                item {
                    Text(
                        "Agregue items a cobrar al cliente..",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

        }
        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = { showItemList = true },
                    shape = RoundedCornerShape(8.dp)
                ) { Text("Agregar item") }
                Spacer(Modifier.size(0.dp))
                Text(
                    "Total: ${productServicesTotal.toPrice()}",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = DarkAccentLime
                )
                Spacer(modifier = Modifier.weight(1f))
                MaxWidthButtonLime("Agregar Pago") {
                    viewModel.addPayment()
                }
                Spacer(modifier = Modifier.size(16.dp))
            }
        }
        if(showItemList) {
            ItemsDialog(productServicesList, onItemSelected = { itemId ->
                showItemList = false
                viewModel.addItemToChart(itemId)
            }) { showItemList = false }
        }
    }
}

@Composable
private fun ItemsDialog(
    productServicesList: List<ProductService>,
    onItemSelected: (Int) -> Unit,
    onDismiss: () -> Unit,
) {

    Dialog(
        onDismissRequest = { onDismiss() }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = CardDark
            )
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .heightIn(max = 500.dp)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TitleWithDivider("Agregar item")
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    productServicesList.forEach { productService ->
                        item {
                            Box(Modifier
                                .fillMaxWidth()
                                .clickable { onItemSelected(productService.id) }) {
                                Text(
                                    productService.label,
                                    modifier = Modifier.fillMaxWidth().padding(4.dp),
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


enum class DragState {
    LEFT, RIGHT
}

@Composable
fun DraggableCardItem(productService: ProductService, onDelete: () -> Unit) {

    val dragState = remember {
        AnchoredDraggableState(
            initialValue = DragState.LEFT,
            anchors = DraggableAnchors {
                DragState.LEFT at 0f
                DragState.RIGHT at 180f
            }
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Red)
    ) {
        Box(
            Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterStart,
        ) {
            Icon(
                Icons.Default.Delete,
                tint = Color.White,
                contentDescription = "delete icon",
                modifier = Modifier
                    .padding(vertical = 4.dp, horizontal = 16.dp)
                    .clickable { onDelete() })
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset { IntOffset(dragState.requireOffset().roundToInt(), y = 0) }
                    .anchoredDraggable(
                        state = dragState,
                        orientation = Orientation.Horizontal
                    )
            ) {
                Card(
                    Modifier
                        .fillMaxWidth()
                        .background(CardDark),
                    colors = CardDefaults.cardColors(containerColor = CardDark)
                ) {
                    Row(
                        Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(productService.label, style = MaterialTheme.typography.labelLarge)
                        Text(
                            productService.amount.toPrice(),
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = DarkAccentLime
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun PaymentSelector(
    paymentSelected: PaymentMethod,
    onPaymentSelected: (PaymentMethod) -> Unit,
) {

    val paymentMethods = listOf(
        CASH,
        TRANSFER
    )

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Método de pago", style = MaterialTheme.typography.titleMedium)
        paymentMethods.forEach { method ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    onPaymentSelected(method)
                }
            ) {
                RadioButton(
                    selected = paymentSelected == method,
                    onClick = { onPaymentSelected(method) },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = DarkAccentLime,
                        unselectedColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
                Text(method.method, style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}

@Composable
private fun ClientSelector(
    clientSelected: String,
    openClientMenu: Boolean,
    clients: List<Client>,
    clientQuery: String,
    onActionDone: (ClientSelectorActions, String?) -> Unit,
) {

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
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 240.dp)
        ) {
            if (clients.isNotEmpty()) {
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
                    trailingIcon = {
                        if (clientQuery.isNotBlank()) Icon(
                            Icons.Default.Clear,
                            contentDescription = "borrar nombre",
                            modifier = Modifier.clickable {
                                onActionDone(
                                    ClientSelectorActions.MODIFY_QUERY_CLIENT, ""
                                )
                            })
                    }
                )
                clients.forEach { client ->
                    DropdownMenuItem(
                        text = { Text("${client.name} ${client.lastName}") },
                        onClick = {
                            onActionDone(
                                ClientSelectorActions.CLIENT_SELECTED,
                                "${client.name} ${client.lastName}"
                            )
                        }
                    )
                }
            } else {
                DropdownMenuItem(
                    text = { Text("No hay clientes registrados") },
                    onClick = { }
                )
            }
        }
    }
}

enum class ClientSelectorActions {
    DISMISS, CLIENT_SELECTED, OPEN_CLIENT_MENU, MODIFY_QUERY_CLIENT
}