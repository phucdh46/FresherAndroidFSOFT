package com.example.assignmetday6jetpack1.vm

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.assignmetday6jetpack1.model.User


class UserViewModel() : ViewModel() {
    private val TAG: String= "DHP"
    val padding = MutableLiveData<Int>(50)

    val username = MutableLiveData("")
    val password = MutableLiveData("")
    val confirm_password = MutableLiveData("")

    var _users = MutableLiveData<ArrayList<User>>()
    val users: LiveData<ArrayList<User>>
        get() = _users
    var listUser = arrayListOf<User>()
    var message = MutableLiveData("")
    fun signUp() {
        val userLogin = User(username.value!!,password.value!!)

        if (username.value == null || !Patterns.EMAIL_ADDRESS.matcher(username.value).matches()) {
            message.value = "Email Invalid"
        } else if (password.value == null || password.value!!.length < 6) {
            message.value = "Password Invalid"
        } else if (!password.value!!.equals(confirm_password.value)) {
            message.value = "Two Password must be match"
        }else   {
                message.value = "SignUp success"
                listUser.add(userLogin)
                _users.value = listUser
            Log.d(TAG,listUser.toString())
            }

    }
    var _checkLogin = MutableLiveData<Boolean>(false)
    val checkLogin: LiveData<Boolean>
        get() = _checkLogin
    fun login() {
        //Log.d(TAG,user.value.toString())
        if (username.value == null || !Patterns.EMAIL_ADDRESS.matcher(username.value).matches()) {
            message.value = "Email Invalid"
        } else if (password.value == null || password.value!!.length < 6) {
            message.value = "Password Invalid"
        } else {

            val userLogin = User(username.value!!,password.value!!)
            if (listUser.contains(userLogin) ){
                message.value = "Login success"
                _checkLogin.value=true
            }else{
                message.value = "Password or email wrong!"
                _checkLogin.value=false
            }
        }
    }
}