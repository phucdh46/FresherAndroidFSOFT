package com.example.day8assignmentteamanagment.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.day8assignmentteamanagment.network.UserResponse
import com.example.day8assignmentteamanagment.network.UserService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

//viewmodel: network from retrofit
@HiltViewModel
class UserViewModel @Inject constructor(
    val userService: UserService
) : ViewModel() {
    var _user = MutableLiveData<UserResponse>()
    val user: LiveData<UserResponse>
        get() = getUser()

    fun getUser(): MutableLiveData<UserResponse> {
        viewModelScope.launch {
            _user.value = userService.getUser()
        }
        return _user
    }
}