package com.example.myapplication.repositories.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.domain.entities.Property
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface PropertyFilterModelDao {

    @Insert
    fun insert(property: Property) : Completable

    @Update
    fun update(property: Property) : Completable

    @Delete
    fun delete(property: Property) : Completable

    @Query("delete from Property")
    fun deleteAllProperties()

    @Query("select * from Property order by id desc")
    fun getAllProperty(): Observable<Property>
}