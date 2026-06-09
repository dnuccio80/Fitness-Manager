package com.danucdev.fitnessmanager.ui.screens.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import com.danucdev.fitnessmanager.R
import com.danucdev.fitnessmanager.ui.core.MainHeader
import com.danucdev.fitnessmanager.ui.navigation.BottomNavigationItem
import com.danucdev.fitnessmanager.ui.screens.main.TransactionSectionAction.*
import com.danucdev.fitnessmanager.ui.theme.DarkAccentLime
import com.danucdev.fitnessmanager.ui.theme.DarkAccentWhite
import com.danucdev.fitnessmanager.ui.theme.MainDark

@Composable
fun MainScreen(
    navigateToPaymentReceived: () -> Unit,
    navigateToAddClient: () -> Unit,
    navigateToAddInvestment: () -> Unit,
    navigateToConfig: () -> Unit,
    navigateToBottomBarAction: (NavKey) -> Unit,
    currentRoute: NavKey,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = { BottomBar(currentRoute) { route -> navigateToBottomBarAction(route) } }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                MainHeader { navigateToConfig() }
                DashboardCardItem()
                TransactionSection { action ->
                    when (action) {
                        NEW_PAYMENT -> {
                            navigateToPaymentReceived()
                        }

                        NEW_CLIENT -> {
                            navigateToAddClient()
                        }

                        NEW_INVESTMENT -> {
                            navigateToAddInvestment()
                        }
                    }
                }
                LastTransactionsSection()
            }
        }
    }
}

@Composable
fun BottomBar(currentRoute: NavKey, onClick: (NavKey) -> Unit) {

    val navItems = listOf(
        BottomNavigationItem.Home,
        BottomNavigationItem.Clients,
        BottomNavigationItem.Transactions,
    )

    NavigationBar {
        navItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = { onClick(item.route) },
                icon = { Icon(painterResource(item.icon), contentDescription = "") },
                label = { Text(item.label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = DarkAccentLime,
                    selectedTextColor = DarkAccentLime
                )
            )
        }
    }

}

@Composable
fun LastTransactionsSection() {
    Card(
        Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    "Ultimas transacciones",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium,
                )
                HorizontalDivider(
                    Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = DarkAccentWhite
                )
            }
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                LastTransactionRowItem(
                    "Pago cuota de Damian Nuccio",
                    painterResource(R.drawable.ic_money),
                    120000
                )
                LastTransactionRowItem(
                    "Pago cuota de Damian Nuccio",
                    painterResource(R.drawable.ic_money),
                    120000
                )
                LastTransactionRowItem(
                    "Pago cuota de Damian Nuccio",
                    painterResource(R.drawable.ic_money),
                    120000
                )
                LastTransactionRowItem(
                    "Pago cuota de Damian Nuccio",
                    painterResource(R.drawable.ic_money),
                    120000
                )

            }
        }
    }
}

@Composable
fun LastTransactionRowItem(label: String, icon: Painter, amount: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimaryContainer)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),

                ) {
                Card(
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(containerColor = DarkAccentLime)
                ) {
                    Icon(icon, contentDescription = null, tint = MainDark)
                }
                Text(label, style = MaterialTheme.typography.labelLarge)
            }
            Text(
                "$$amount",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = DarkAccentLime
            )
        }
    }
}


@Composable
private fun DashboardCardItem() {
    Card(
        Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    "RESUMEN",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                HorizontalDivider(
                    Modifier.fillMaxWidth(),
                    thickness = 1.dp, color = MaterialTheme.colorScheme.onPrimary
                )
            }
            DetailsRowWithIcon("Ingresos del mes: $1.400.000", painterResource(R.drawable.ic_money))
            DetailsRowWithIcon("Gastos del mes: $400.000", painterResource(R.drawable.ic_bag))
            DetailsRowWithIcon("Usuarios activos: 140", painterResource(R.drawable.ic_person))
            DetailsRowWithIcon("Cuotas pendientes: 80", painterResource(R.drawable.ic_task))
            DetailsRowWithIcon(
                "Usuarios inactivos: 100",
                painterResource(R.drawable.ic_person_remove)
            )
            DetailsRowWithIcon(
                "Valor de cuota actual: $40.000",
                painterResource(R.drawable.ic_calendar)
            )
        }
    }
}

@Composable
private fun TransactionSection(onActionDone: (TransactionSectionAction) -> Unit) {
    LazyRow(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        item {
            ActionCardItem("Nuevo cobro", painterResource(R.drawable.ic_earn)) {
                onActionDone(
                    NEW_PAYMENT
                )
            }
        }
        item {
            ActionCardItem("Nuevo cliente", painterResource(R.drawable.ic_person_add)) {
                onActionDone(
                    NEW_CLIENT
                )
            }
        }
        item {
            ActionCardItem("Nuevo gasto", painterResource(R.drawable.ic_bag)) {
                onActionDone(
                    NEW_INVESTMENT
                )
            }
        }

    }
}

enum class TransactionSectionAction {
    NEW_PAYMENT, NEW_CLIENT, NEW_INVESTMENT
}

@Composable
private fun DetailsRowWithIcon(label: String, icon: Painter) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(icon, contentDescription = null, tint = DarkAccentLime)
        Text(
            label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
private fun ActionCardItem(label: String, icon: Painter, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .size(100.dp)
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Icon(
                icon,
                modifier = Modifier.size(30.dp),
                tint = DarkAccentLime,
                contentDescription = null
            )
            Text(
                label,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}