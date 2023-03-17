package com.example.list_roompractice.data

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.list_roompractice.util.Converters
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity("user_table")
@TypeConverters(Converters::class)
data class User(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    @ColumnInfo(name = "firstName")
    val firstName:String,
    @ColumnInfo(name = "lastName")
    val lastName:String,
    @ColumnInfo(name = "age")
    val age:Int,
    @ColumnInfo(name = "pic")
    val profilePic:Bitmap
):Parcelable
