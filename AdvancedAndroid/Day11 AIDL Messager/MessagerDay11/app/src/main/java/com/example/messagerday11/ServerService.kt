package com.example.messagerday11

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.*
import android.util.Log


val MSG_SAY_HELLO = 1
val MSG_SAY_BYE = 2
var mClient: Messenger? = null
val SERVER_TO_CLIENT = 3

val TAG: String = "DHP"

class ServerService : Service() {
    private lateinit var mMessenger: Messenger

    internal class IncomingHandler(
        context: Context,
        private val applicationContext: Context = context.applicationContext
    ) : Handler(Looper.getMainLooper()) {

        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MSG_SAY_HELLO -> {
                    mClient = msg.replyTo
                    //receive data type primitive
                    // Log.d(TAG, "server: client say hello: ${msg.obj.toString() }")
                    //receive data type object
                    val user: User = msg.obj as User
                    Log.d(TAG, "server: client say hello ${user.name}")
                }
                MSG_SAY_BYE -> {
                    mClient = msg.replyTo
                    Log.d(TAG, "server: client say bye: ${msg.obj.toString()}")
                }
                else -> super.handleMessage(msg)
            }
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        mMessenger = Messenger(IncomingHandler(this))
        return mMessenger.binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        //mClient= null
        return super.onUnbind(intent)
    }

    open fun sendMessage() {
        if (mClient == null) {
            Log.d(TAG, "server: client is null: ${mClient}")
        }
        val msg: Message = Message.obtain(null, SERVER_TO_CLIENT)
        msg.obj = "This is Server"
        mClient?.send(msg)
    }
}