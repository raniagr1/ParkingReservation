package com.example.trying.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.trying.data.dao.UserDao
import com.example.trying.data.model.User

class UserViewModel(private val userDao: UserDao) : ViewModel() {
     suspend fun getUser(userId: Long): User? {
        return userDao.getUser(userId)
    }

    suspend fun register(user: User) {
        userDao.register(user)
    }
}
