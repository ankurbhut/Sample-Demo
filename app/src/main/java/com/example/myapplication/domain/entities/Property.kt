package com.example.myapplication.domain.entities


import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.model.Exclusion
import com.example.myapplication.model.Facility

@Keep
@Entity
class Property() {
    var exclusions: ArrayList<ArrayList<Exclusion>> = ArrayList()

    var facilities: ArrayList<Facility> = ArrayList()

    @PrimaryKey(autoGenerate = true)
    var id: Int? = 0

    constructor(facilities: ArrayList<Facility>, exclusions: ArrayList<ArrayList<Exclusion>>): this(){
        this.facilities = facilities
        this.exclusions = exclusions
    }
}