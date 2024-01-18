package com.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.core.database.entity.DictionaryEntity


@Dao
interface DictionaryDao {

    @Query("Select * from a_Group")
    suspend fun getDictionaryData (): List<DictionaryEntity>


    @Query("Select * from a_Group ORDER BY RANDOM() LIMIT 3")
    suspend fun getRandomOption (): List<DictionaryEntity>

    @Query("Select * from a_Group ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomWord (): DictionaryEntity


}