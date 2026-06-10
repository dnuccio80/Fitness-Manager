package com.danucdev.fitnessmanager.ui.screens.transactions.transactionslist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.graphics.Brush
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
import com.danucdev.fitnessmanager.ui.screens.transactions.transactionslist.TransactionsViewMode.*
import com.danucdev.fitnessmanager.ui.theme.AccentColor
import com.danucdev.fitnessmanager.ui.theme.AccentColor2
import com.danucdev.fitnessmanager.ui.theme.CardDark
import com.danucdev.fitnessmanager.ui.theme.ErrorContainer

@Composable
fun TransactionsScreen(
    currentRoute: NavKey,
    viewModel: TransactionsViewModel = hiltViewModel(),
    onBottomBarClick: (NavKey) -> Unit,
) {

    val transactions by viewModel.transactions.collectAsStateWithLifecycle()
    val viewMode by viewModel.transactionsViewMode.collectAsStateWithLifecycle()

    ScreenContainerWithBottomBar(
        headerLabel = "Transacciones",
        currentRoute = currentRoute,
        onBottomBarClick = { onBottomBarClick(it) }
    ) {
        if (transactions.isNotEmpty()) {
            Text(
                "Listado de transacciones",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge
            )
            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                SegmentedButton(
                    selected = viewMode == EARNS,
                    onClick = { viewModel.updateTransactionViewMode(EARNS) },
                    shape = SegmentedButtonDefaults.itemShape(0, 3),
                    label = { Text(EARNS.value) }
                )
                SegmentedButton(
                    selected = viewMode == EXPENSES,
                    onClick = { viewModel.updateTransactionViewMode(EXPENSES) },
                    shape = SegmentedButtonDefaults.itemShape(1, 3),
                    label = { Text(EXPENSES.value) }
                )
                SegmentedButton(
                    selected = viewMode == ALL,
                    onClick = { viewModel.updateTransactionViewMode(ALL) },
                    shape = SegmentedButtonDefaults.itemShape(2, 3),
                    label = { Text(ALL.value) }
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

    val color = if (data.isEarn) AccentColor2 else ErrorContainer

    Card(
        Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = color),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            CardDark,
                            CardDark,
                            color,
                        )
                    )
                )
                .fillMaxWidth()
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    data.description,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    data.amount.toPrice(),
                    maxLines = 1,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

    }

}