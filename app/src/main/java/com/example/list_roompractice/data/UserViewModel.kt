package com.example.list_roompractice.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application):AndroidViewModel(application) {

    val readAllUsersData:LiveData<List<User>>
    private val repository:UserRepository

    init {
        val userDao = UserDatabase.getInstance(application).userDao
        repository = UserRepository(userDao)
        readAllUsersData = repository.allUsersData
    }

    fun addUser(user:User){
        viewModelScope.launch(Dispatchers.IO){
            repository.addUser(user)
        }
    }
}