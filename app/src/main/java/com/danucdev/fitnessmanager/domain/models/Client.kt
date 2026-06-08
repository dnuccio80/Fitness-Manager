package com.danucdev.fitnessmanager.domain.models

import com.danucdev.fitnessmanager.data.entities.ClientEntity

data class Client(
    val clientId:Int,
    val name:String,
    val phone:String
) {
    fun toEntity(): ClientEntity {
        return ClientEntity(
            clientId = clientId,
            name = name,
            phone = phone
        )
    }
}
