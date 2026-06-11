package com.danucdev.fitnessmanager.domain.models

import com.danucdev.fitnessmanager.data.entities.ClientEntity

data class Client(
    val clientId:Int = 0,
    val name:String,
    val lastName:String,
    val phone: Long
) {
    fun toEntity(): ClientEntity {
        return ClientEntity(
            clientId = clientId,
            name = name,
            lastName = lastName,
            phone = phone
        )
    }
}
