package com.amit.nasaa.pod.ui.main.database

import androidx.room.*

@Dao
interface APODDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: APODEntity)

    @Query("DELETE FROM apod")
    fun deleteAll()

    @Update
    fun update(entity: APODEntity)

    @Query("SELECT * from apod LIMIT 1")
    fun getAPOD(): APODEntity
}