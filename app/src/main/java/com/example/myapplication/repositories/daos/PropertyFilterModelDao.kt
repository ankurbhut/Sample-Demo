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
    fun insert(note: Property) : Completable

    @Update
    fun update(note: Property) : Completable

    @Delete
    fun delete(note: Property) : Completable

    @Query("delete from Property")
    fun deleteAllNotes()

    @Query("select * from Property order by id desc")
    fun getAllNotes(): Observable<Property>
}