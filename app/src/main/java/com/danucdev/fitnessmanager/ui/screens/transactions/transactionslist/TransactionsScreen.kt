package com.danucdev.fitnessmanager.ui.screens.transactions.transactionslist

import android.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import com.danucdev.fitnessmanager.domain.models.Transaction
import com.danucdev.fitnessmanager.ui.core.ScreenContainerWithBottomBar
import com.danucdev.fitnessmanager.ui.core.ex.toPrice
import com.danucdev.fitnessmanager.ui.theme.DarkAccentLime

@Composable
fun TransactionsScreen(
    currentRoute: NavKey,
    viewModel: TransactionsViewModel = hiltViewModel(),
    onBottomBarClick: (NavKey) -> Unit,
) {

    val transactions by viewModel.transactions.collectAsStateWithLifecycle()
    ScreenContainerWithBottomBar(
        headerLabel = "Transacciones",
        currentRoute = currentRoute,
        onBottomBarClick = { onBottomBarClick(it) }
    ) {
        if (transactions.isNotEmpty()) {
            Text("Listado de transacciones", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)
            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                SegmentedButton(
                    selected = false,
                    onClick = {  },
                    shape = SegmentedButtonDefaults.itemShape(0, 3),
                    label = { Text("Ingresos") }
                )
                SegmentedButton(
                    selected = false,
                    onClick = {  },
                    shape = SegmentedButtonDefaults.itemShape(1, 3),
                    label = { Text("Gastos") }
                )
                SegmentedButton(
                    selected = true,
                    onClick = { },
                    shape = SegmentedButtonDefaults.itemShape(2, 3),
                    label = { Text("Ver todo") }
                )

            }


            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(transactions) { transaction ->
                    TransactionCard(transaction)
                }
            }
        } else {
            Text("No hay transacciones para mostrar")
        }
    }

}

@Composable
private fun TransactionCard(data: Transaction) {

    val color = if (data.isEarn) DarkAccentLime else MaterialTheme.colorScheme.errorContainer

    Card(
        Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(containerColor = color),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(data.description, overflow = TextOverflow.Ellipsis)
            Text(data.amount.toPrice())
        }
    }

}