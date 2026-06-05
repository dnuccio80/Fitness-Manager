package com.danucdev.fitnessmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.danucdev.fitnessmanager.ui.screens.investment.InvestmentScreen
import com.danucdev.fitnessmanager.ui.theme.FitnessManagerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var isDarkTheme by rememberSaveable { mutableStateOf(true) }

            FitnessManagerTheme(darkTheme = isDarkTheme) {
                InvestmentScreen()
            }
        }
    }
}

