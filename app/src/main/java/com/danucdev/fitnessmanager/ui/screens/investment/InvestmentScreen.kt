package com.danucdev.fitnessmanager.ui.screens.investment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.danucdev.fitnessmanager.ui.core.BackIconButton
import com.danucdev.fitnessmanager.ui.core.Header
import com.danucdev.fitnessmanager.ui.core.TextFieldForNamesItem
import com.danucdev.fitnessmanager.ui.core.TextFieldForSentencesItem
import com.danucdev.fitnessmanager.ui.theme.DarkAccentLime
import com.danucdev.fitnessmanager.ui.theme.MainDark

@Composable
fun InvestmentScreen() {

    var details by rememberSaveable { mutableStateOf("") }
    var amount by rememberSaveable { mutableStateOf("") }

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
            Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Header("Agregar nuevo gasto")
                BackIconButton {
                    //TODO WHEN NAVIGATION IS DONE
                }
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
                    value = details,
                    placeholder = "Detalle..",
                    onValueChange = { details = it }
                )
                TextFieldForNamesItem(
                    value = amount,
                    placeholder = "Monto..",
                    numberOnly = true,
                    onValueChange = { amount = it }
                )
                Spacer(Modifier.weight(1f))
                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = DarkAccentLime, contentColor = MainDark),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text("Agregar gasto", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                }
                Spacer(Modifier.size(16.dp))

            }
        }
    }

}