package com.example.list_roompractice.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    @ColumnInfo(name = "firstName")
    val firstName:String,
    @ColumnInfo(name = "lastName")
    val lastName:String,
    @ColumnInfo(name = "age")
    val age:Int,
)
