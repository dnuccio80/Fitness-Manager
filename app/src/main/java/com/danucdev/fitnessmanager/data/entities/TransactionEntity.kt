package com.danucdev.fitnessmanager.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.danucdev.fitnessmanager.domain.models.Transaction

@Entity
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val isEarn: Boolean,
    val amount:String,
    val description:String,
) {
    fun toDomain(): Transaction {
        return Transaction(
            id = id,
            isEarn = isEarn,
            amount = amount,
            description = description
        )
    }
}