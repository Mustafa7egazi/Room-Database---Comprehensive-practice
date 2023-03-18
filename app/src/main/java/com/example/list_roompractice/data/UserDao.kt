package com.example.list_roompractice.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user:User)

    @Update
    suspend fun updateUser(user:User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("DELETE FROM user_table")
    suspend fun deleteAllData()

    @Query("SELECT * FROM user_table ORDER BY id ASC")
     fun getAllUsersData():LiveData<List<User>>

     @Query("SELECT * FROM user_table WHERE firstName LIKE :searchQuery OR lastName LIKE :searchQuery")
     fun searchInDatabase(searchQuery:String):LiveData<List<User>>
}