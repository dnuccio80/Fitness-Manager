package com.danucdev.fitnessmanager.domain.models

import com.danucdev.fitnessmanager.data.entities.TransactionEntity

data class Transaction(
    val id:Int = 0,
    val isEarn: Boolean,
    val amount:String,
    val description:String,
) {
   fun toEntity(): TransactionEntity {
       return TransactionEntity(
           id = id,
           isEarn = isEarn,
           amount = amount,
           description = description
       )
   }
}
