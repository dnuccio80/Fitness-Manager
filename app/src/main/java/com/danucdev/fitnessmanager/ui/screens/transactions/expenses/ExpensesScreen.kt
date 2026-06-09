package com.danucdev.fitnessmanager.ui.screens.transactions.expenses

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.danucdev.fitnessmanager.ui.core.ErrorText
import com.danucdev.fitnessmanager.ui.core.MaxWidthButtonLime
import com.danucdev.fitnessmanager.ui.core.ScreenContainer
import com.danucdev.fitnessmanager.ui.core.TextFieldForNamesItem
import com.danucdev.fitnessmanager.ui.core.TextFieldForSentencesItem
import com.danucdev.fitnessmanager.ui.theme.DarkAccentLime

@Composable
fun ExpensesScreen(viewModel: ExpensesViewModel = hiltViewModel(), onBack: () -> Unit) {

    val expenseData by viewModel.expenseData.collectAsStateWithLifecycle()

    ScreenContainer("Agregar nuevo gasto", onBack = { onBack() }) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Agendar nuevo gasto",
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
        TextFieldForSentencesItem(
            value = expenseData.details,
            placeholder = "Detalle..",
            onValueChange = { viewModel.updateDetails(it) }
        )
        TextFieldForNamesItem(
            value = expenseData.amount,
            placeholder = "Monto..",
            numberOnly = true,
            onValueChange = { viewModel.updateAmount(it) }
        )
        AnimatedVisibility(!expenseData.isAllData) {
            ErrorText("Faltan datos por completar!")
        }
        Column {
            Spacer(Modifier.weight(1f))
            MaxWidthButtonLime("Agregar gasto") { viewModel.addExpense() }
            Spacer(Modifier.size(16.dp))
        }
    }
}