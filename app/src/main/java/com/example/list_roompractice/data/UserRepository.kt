package com.example.list_roompractice.data

class UserRepository(private val userDao: UserDao) {


    val allUsersData = userDao.getAllUsersData()

    suspend fun addUser(user:User){
        userDao.addUser(user)
    }

    suspend fun updateUser(user: User){
        userDao.updateUser(user)
    }

    suspend fun deleteUser(user: User){
        userDao.deleteUser(user)
    }

    suspend fun deleteAllData(){
        userDao.deleteAllData()
    }
}