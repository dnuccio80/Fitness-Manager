package com.danucdev.fitnessmanager.ui.core.ex

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

fun NavBackStack<NavKey>.navigateTo(screen: NavKey) {
    add(screen)
}

fun NavBackStack<NavKey>.back() {
    if(isEmpty()) return
    removeLastOrNull()
}

fun NavBackStack<NavKey>.backTo(targetScreen: NavKey) {
    if(isEmpty()) return
    if(targetScreen !in this) navigateTo(targetScreen)
    while(isNotEmpty() && last() != targetScreen) {
        removeLastOrNull()
    }
}