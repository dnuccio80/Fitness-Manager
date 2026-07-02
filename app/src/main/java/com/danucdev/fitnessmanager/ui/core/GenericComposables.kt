package com.danucdev.fitnessmanager.ui.core

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation3.runtime.NavKey
import com.danucdev.fitnessmanager.R
import com.danucdev.fitnessmanager.ui.core.ex.toPrice
import com.danucdev.fitnessmanager.ui.screens.main.BottomBar
import com.danucdev.fitnessmanager.ui.theme.DarkAccentGray
import com.danucdev.fitnessmanager.ui.theme.DarkAccentLime
import com.danucdev.fitnessmanager.ui.theme.DarkAccentWhite
import com.danucdev.fitnessmanager.ui.theme.DarkTextPrimary
import com.danucdev.fitnessmanager.ui.theme.DarkTextSecondary
import com.danucdev.fitnessmanager.ui.theme.MainDark


@Composable
fun ScreenContainer(
    headerLabel: String,
    onBack: ColumnScope.() -> Unit,
    content: @Composable () -> Unit,
) {
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
                NormalHeader(headerLabel)
                BackIconButton {
                    onBack()
                }
                content()
            }
        }
    }
}

@Composable
fun ScreenContainerWithBottomBar(
    headerLabel: String,
    currentRoute: NavKey,
    onBottomBarClick: (NavKey) -> Unit,
    content: @Composable () -> Unit,
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = { BottomBar(currentRoute) { route -> onBottomBarClick(route) } }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                NormalHeader(headerLabel)
                content()
            }
        }
    }

}

@Composable
fun ErrorText(text: String) {
    Text(text, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.error)
}

@Composable
fun DescriptionCardWithPrice(title: String, amount: Long, onClick: () -> Unit) {
    Card(
        Modifier.fillMaxWidth().clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                title,
                style = MaterialTheme.typography.labelLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                amount.toPrice(),
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = DarkAccentLime,
                maxLines = 1,
            )
        }

    }
}

@Composable
fun MainHeader(onNavigateToConfig: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        NormalHeader("Profe Elias")
        Spacer(Modifier.weight(1f))
        Icon(
            Icons.Filled.Settings,
            contentDescription = "Configuraciones",
            modifier = Modifier.clickable {
                onNavigateToConfig()
            })
    }
}

@Composable
fun TitleWithDivider(label:String) {
    Column {
        Text(label, style = MaterialTheme.typography.titleLarge)
        HorizontalDivider(Modifier.fillMaxWidth(), thickness = 1.dp, DarkAccentLime)
    }
}

@Composable
fun NormalHeader(label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier.size(50.dp),
            shape = CircleShape,
            border = BorderStroke(2.dp, DarkAccentWhite)
        ) {
            Image(
                painterResource(R.drawable.logo),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Column {
            Text(
                "Casa de entrenamiento",
                color = DarkTextSecondary,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                label,
                color = DarkTextPrimary,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun BackIconButton(onClick: () -> Unit) {
    IconButton(onClick = { onClick() }) {
        Icon(
            Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "back button",
            tint = DarkAccentWhite
        )
    }
}

@Composable
fun ConfirmDialog(text: String, onConfirm: () -> Unit, onDismiss: () -> Unit) {
    Dialog(
        onDismissRequest = { onDismiss() }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text,
                    style = MaterialTheme.typography.titleSmall,
                    color = DarkAccentWhite,
                    textAlign = TextAlign.Center
                )
                AcceptDeclineButtonItem(onConfirm = onConfirm, onDismiss = onDismiss)
            }
        }
    }
}

@Composable
fun AcceptDeclineButtonItem(
    acceptLabel: String = "Aceptar",
    cancelLabel: String = "Cancelar",
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(
            onClick = { onDismiss() },
            colors = ButtonDefaults.buttonColors(containerColor = DarkAccentGray),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                cancelLabel,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
        }
        Button(
            onClick = { onConfirm() },
            colors = ButtonDefaults.buttonColors(
                containerColor = DarkAccentLime,
                contentColor = MainDark
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                acceptLabel,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun TextFieldForNamesItem(
    value: String,
    placeholder: String,
    numberOnly: Boolean = false,
    onValueChange: (String) -> Unit,
) {
    TextField(
        value = if (numberOnly) value.filter { it.isDigit() } else value,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(placeholder) },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent
        ),
        keyboardOptions = if (numberOnly) KeyboardOptions(keyboardType = KeyboardType.Number) else KeyboardOptions(
            capitalization = KeyboardCapitalization.Words
        ),
        singleLine = true,
        maxLines = 1,
        onValueChange = { onValueChange(it) })
}

@Composable
fun TextFieldForSentencesItem(
    value: String,
    placeholder: String,
    enabled: Boolean = true,
    numberOnly: Boolean = false,
    onValueChange: (String) -> Unit,
) {
    TextField(
        value = if (numberOnly) value.filter { it.isDigit() } else value,
        modifier = Modifier.fillMaxWidth(),
        enabled = enabled,
        placeholder = { Text(placeholder) },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent
        ),
        keyboardOptions = if (numberOnly) KeyboardOptions(keyboardType = KeyboardType.Number) else KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences
        ),
        singleLine = true,
        maxLines = 1,
        onValueChange = { onValueChange(it) })
}

@Composable
fun MaxWidthButtonLime(label: String, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = DarkAccentLime,
            contentColor = MainDark
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(4.dp)
    ) {
        Text(
            label,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
    }
}