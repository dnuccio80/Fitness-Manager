package com.danucdev.fitnessmanager.ui.core

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.danucdev.fitnessmanager.R
import com.danucdev.fitnessmanager.ui.theme.DarkAccentWhite
import com.danucdev.fitnessmanager.ui.theme.DarkTextPrimary
import com.danucdev.fitnessmanager.ui.theme.DarkTextSecondary

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
fun BackIconButton(onClick:() -> Unit) {
    IconButton(onClick = { onClick() }) {
        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "back button", tint = DarkAccentWhite)
    }
}