package com.codepath.BitFit_Part2
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DrinkDao {
    @Query("SELECT * FROM drink_table")
    fun getAll(): Flow<List<DrinkEntity>>

    @Insert
    fun insertAll(drinks: List<DrinkEntity>)

    @Insert
    fun insertOne(drink: DrinkEntity)

    @Query("DELETE FROM drink_table")
    fun deleteAll()
}