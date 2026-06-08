package com.danucdev.fitnessmanager.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ClientEntity(
    @PrimaryKey(autoGenerate = true)
    val clientId:Int,
    val name:String,
    val phone:String
)
