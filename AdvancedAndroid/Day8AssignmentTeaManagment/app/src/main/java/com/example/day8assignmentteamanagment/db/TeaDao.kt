package com.example.day8assignmentteamanagment.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TeaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTea(tea: Tea)

    @Update
    suspend fun updateTea(tea: Tea)

    @Query("DELETE FROM tb_tea WHERE id=:id")
    suspend fun deleteTea(id: Int)

    @Query("SELECT * FROM tb_tea")
    fun getAllTeas(): LiveData<List<Tea>>
}