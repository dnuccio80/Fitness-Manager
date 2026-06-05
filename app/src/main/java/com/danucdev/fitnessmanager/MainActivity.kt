package com.danucdev.fitnessmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.danucdev.fitnessmanager.ui.core.ex.back
import com.danucdev.fitnessmanager.ui.core.ex.navigateTo
import com.danucdev.fitnessmanager.ui.navigation.NavRoutes
import com.danucdev.fitnessmanager.ui.navigation.NavRoutes.AddClient
import com.danucdev.fitnessmanager.ui.navigation.NavRoutes.ClientDetails
import com.danucdev.fitnessmanager.ui.navigation.NavRoutes.Clients
import com.danucdev.fitnessmanager.ui.navigation.NavRoutes.Config
import com.danucdev.fitnessmanager.ui.navigation.NavRoutes.Investment
import com.danucdev.fitnessmanager.ui.navigation.NavRoutes.Main
import com.danucdev.fitnessmanager.ui.navigation.NavRoutes.Payment
import com.danucdev.fitnessmanager.ui.screens.MainScreen
import com.danucdev.fitnessmanager.ui.screens.SettingsScreen
import com.danucdev.fitnessmanager.ui.screens.clients.AddClientScreen
import com.danucdev.fitnessmanager.ui.screens.clients.ClientDetailsScreen
import com.danucdev.fitnessmanager.ui.screens.clients.ClientsScreen
import com.danucdev.fitnessmanager.ui.screens.investment.InvestmentScreen
import com.danucdev.fitnessmanager.ui.screens.transactions.PaymentsScreen
import com.danucdev.fitnessmanager.ui.theme.FitnessManagerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var isDarkTheme by rememberSaveable { mutableStateOf(true) }
            val backStack = rememberNavBackStack(Main)

            FitnessManagerTheme(darkTheme = isDarkTheme) {
                NavDisplay(
                    backStack = backStack,
                    onBack = { backStack.back() },
                    entryProvider = entryProvider {
                        entry<Main> {
                            MainScreen(
                                navigateToPaymentReceived = { backStack.navigateTo(Payment) },
                                navigateToAddClient = { backStack.navigateTo(AddClient) },
                                navigateToAddInvestment = { backStack.navigateTo(Investment) },
                                navigateToConfig = { backStack.navigateTo(Config) }
                            )
                        }
                        entry<Clients> { ClientsScreen() }
                        entry<Payment> { PaymentsScreen { backStack.back() } }
                        entry<AddClient> { AddClientScreen(onBack = { backStack.back() }) }
                        entry<Investment> { InvestmentScreen(onBack = { backStack.back() }) }
                        entry<ClientDetails> { key -> ClientDetailsScreen(key.clientId) }
                        entry<Config> { SettingsScreen { backStack.back() } }
                    },
                    transitionSpec = {
                        slideInHorizontally(
                            initialOffsetX = { it },
                            animationSpec = tween(300)
                        ) togetherWith slideOutHorizontally(
                            targetOffsetX = { -it },
                            animationSpec = tween(300)
                        )
                    },
                    popTransitionSpec = {
                        slideInHorizontally(
                            initialOffsetX = { -it },
                            animationSpec = tween(300)
                        ) togetherWith slideOutHorizontally(
                            targetOffsetX = { it },
                            animationSpec = tween(300)
                        )
                    }
                )
            }
        }
    }
}

