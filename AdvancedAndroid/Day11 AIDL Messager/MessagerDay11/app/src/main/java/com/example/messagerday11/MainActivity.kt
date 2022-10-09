package com.example.messagerday11

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import com.example.messagerday11.databinding.ActivityMainBinding

class MainActivity : Activity() {
    private var mService: Messenger? = null
    private var bound: Boolean = false
    private val mConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            mService = Messenger(service)
            bound = true
        }

        override fun onServiceDisconnected(className: ComponentName) {
            mService = null
            bound = false
        }
    }
    var mMesseger = Messenger(ClientHandler())
    fun sayHello() {
        if (!bound) return
        // Create and send a message to the service
        val msg: Message = Message.obtain(null, MSG_SAY_HELLO, 0, 0)
        msg.obj = "This is Client"
        msg.obj = 1
        msg.obj = 1f
        msg.obj = User("a", 1)
        msg.replyTo = mMesseger
        try {
            mService?.send(msg)

        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    fun sayBye() {
        if (!bound) return
        // Create and send a message to the service
        val msg: Message = Message.obtain(null, MSG_SAY_BYE, 0, 0)
        msg.obj = "This is Client"
        msg.replyTo = mMesseger
        try {
            mService?.send(msg)
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnStart.setOnClickListener() {
            sayHello()
        }
        binding.btnStop.setOnClickListener() {
            sayBye()
        }
        binding.btnSTC.setOnClickListener() {
            val service = ServerService()
            service.sendMessage()
        }
    }

    override fun onStart() {
        super.onStart()
        // Bind to the service
        Intent(this, ServerService::class.java).also { intent ->
            bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        // Unbind from the service
        if (bound) {
            unbindService(mConnection)
            bound = false
        }
    }
}

class ClientHandler : Handler(Looper.getMainLooper()) {
    override fun handleMessage(msg: Message) {
        //receive message from server
        when (msg.what) {
            SERVER_TO_CLIENT -> Log.d(TAG, "client: server say to client")
            else -> super.handleMessage(msg)
        }
    }
}
