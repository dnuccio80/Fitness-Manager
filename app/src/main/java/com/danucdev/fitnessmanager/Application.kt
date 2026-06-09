package com.danucdev.fitnessmanager

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class FitnessApplication @Inject constructor(): Application()