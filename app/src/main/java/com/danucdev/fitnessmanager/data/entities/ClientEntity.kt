package com.danucdev.fitnessmanager.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.danucdev.fitnessmanager.domain.models.Client

@Entity
data class ClientEntity(
    @PrimaryKey(autoGenerate = true)
    val clientId:Int,
    val name:String,
    val lastName:String,
    val phone: Long
) {
    fun toDomain(): Client {
        return Client(
            clientId = clientId,
            name = name,
            lastName = lastName,
            phone = phone
        )
    }
}
