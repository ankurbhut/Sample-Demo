package com.example.myapplication.repositories.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myapplication.domain.entities.Property
import com.example.myapplication.repositories.converter.ArrayConverters
import com.example.myapplication.repositories.converter.Converters
import com.example.myapplication.repositories.daos.PropertyFilterModelDao

@Database(entities = [Property::class], version = 1, exportSchema = false)
@TypeConverters(ArrayConverters::class, Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun propertyDao() : PropertyFilterModelDao
}