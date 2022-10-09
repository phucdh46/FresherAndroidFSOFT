package com.example.day1designarchitecture.presenter

import android.content.Context
import com.example.day1designarchitecture.model.User

interface LoginInterface {
    interface Model {
        interface OnFinishedListener {
            fun onFinished(user: User)
            fun onFailure(t: Throwable?)
        }
        fun getUser(onFinishedListener: OnFinishedListener)
    }

    interface View {
        fun loginSuccess(message: String)
        fun loginError(message: String)
    }

    interface Presenter {
        fun requestDataFromServer()
        fun login(user: User, context: Context, isChecked: Boolean)
        fun checkRemember(isChecked: String?)
    }
}