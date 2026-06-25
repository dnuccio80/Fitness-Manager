package com.danucdev.fitnessmanager.ui.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.danucdev.fitnessmanager.ui.core.ScreenContainer
import com.danucdev.fitnessmanager.ui.core.TitleWithDivider
import com.danucdev.fitnessmanager.ui.theme.AccentColor
import com.danucdev.fitnessmanager.ui.theme.DarkAccentLime
import com.danucdev.fitnessmanager.ui.theme.DarkButton
import com.danucdev.fitnessmanager.ui.theme.DarkIconsAccent

@Composable
fun SettingsScreen(onBack: () -> Unit) {

    var darkMode by rememberSaveable { mutableStateOf(true) }

    ScreenContainer(
        headerLabel = "Configuración",
        onBack = { onBack() }
    ) {
        TitleWithDivider("Productos y sevicios")
        Text("Tenes un total de 3 servicios y 20 productos")
        Button(
            onClick = { },
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DarkButton)
        ) { Text("Administrar productos y servicios") }
        TitleWithDivider("Tema de la app")
        Card(
            Modifier.fillMaxWidth().clickable {
                darkMode = !darkMode
            },
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
            elevation = CardDefaults.cardElevation(2.dp)
        ){
            Row(
                Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Modo oscuro", fontWeight = FontWeight.Bold)
                Switch(
                    checked = darkMode,
                    onCheckedChange = { darkMode = !darkMode },
                    colors = SwitchDefaults.colors(
                        checkedBorderColor = DarkIconsAccent
                    )
                )
            }
        }

    }

}

