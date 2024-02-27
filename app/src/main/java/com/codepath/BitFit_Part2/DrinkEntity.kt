package com.codepath.BitFit_Part2

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "drink_table")
data class DrinkEntity (
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "drinkTitle") val drinkTitle: String?,
    @ColumnInfo(name = "volumeAmt") val volumeAmt: Double?,
    @ColumnInfo(name = "sugarAmt") val sugarAmt: Double?,
)