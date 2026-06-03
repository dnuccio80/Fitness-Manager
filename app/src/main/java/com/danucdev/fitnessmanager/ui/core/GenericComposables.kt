package com.danucdev.fitnessmanager.ui.core

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.danucdev.fitnessmanager.R
import com.danucdev.fitnessmanager.ui.theme.DarkAccentGray
import com.danucdev.fitnessmanager.ui.theme.DarkAccentLime
import com.danucdev.fitnessmanager.ui.theme.DarkAccentWhite
import com.danucdev.fitnessmanager.ui.theme.DarkTextPrimary
import com.danucdev.fitnessmanager.ui.theme.DarkTextSecondary
import com.danucdev.fitnessmanager.ui.theme.MainDark

@Composable
fun Header(label: String) {
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
                AcceptDeclineButtonItem(onConfirm = onConfirm, onDismiss =  onDismiss)
            }
        }
    }
}

@Composable
fun AcceptDeclineButtonItem(acceptLabel:String = "Aceptar", cancelLabel:String = "Cancelar", onConfirm: () -> Unit, onDismiss: () -> Unit) {
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
fun EditClientInfoRowItem(
    value: String,
    placeholder: String,
    numberOnly: Boolean = false,
    onValueChange: (String) -> Unit,
) {
    TextField(
        value = if(numberOnly) value.filter { it.isDigit() } else value,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(placeholder) },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent
        ),
        keyboardOptions = if(numberOnly)KeyboardOptions(keyboardType = KeyboardType.Number) else KeyboardOptions(capitalization = KeyboardCapitalization.Words),
        singleLine = true,
        maxLines = 1,
        onValueChange = { onValueChange(it) })
}