package com.example.myapplication
import kotlinx.coroutines.flow.Flow

class MainRepository(private val userDao: UserDao) {

    val readAllData: Flow<List<User>> = userDao.getAllUsers()

    fun addUser(user: User) {
        userDao.addUser(user)
    }
    fun insertAllUsers(users: User) {
        userDao.insertAllUsers(users)
    }
    fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }
}