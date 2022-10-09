package com.example.day1designarchitecture.network

import android.util.Log
import com.example.day1designarchitecture.model.User
import com.example.day1designarchitecture.presenter.LoginInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class UserService : LoginInterface.Model {
    private val TAG = "DHP"

    override fun getUser(onFinishedListener: LoginInterface.Model.OnFinishedListener) {
        val result = RetrofitHelper.getInstance().create(ApiInterface::class.java).getUsers()
        result.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                //lấy user từ API thành công
                val user = response.body()
                Log.d(TAG, "data: ${user.toString()}")
                onFinishedListener.onFinished(user)
            }

            override fun onFailure(call: Call<User>?, t: Throwable?) {
                //lỗi kết nối đến API
                Log.d(TAG, "${t.toString()}")
                onFinishedListener.onFailure(t)
            }
        })
    }
}
