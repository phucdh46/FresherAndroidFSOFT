package com.example.day7basicandroid

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.day7basicandroid.databinding.FragmentPlayingBinding


class PlayingFragment : DialogFragment() {
    private var isBound: Boolean = false
    private lateinit var playingService: PlayingService
    private lateinit var intent: Intent
    private var bundle = Bundle()
    private var serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, service: IBinder?) {
            var binder = service as PlayingService.LocalBinder
            playingService = binder.getPlayingService()
            isBound = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            isBound = false
        }

    }
    private lateinit var binding: FragmentPlayingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPlayingBinding.inflate(inflater, container, false)
        dialog?.window?.setGravity(Gravity.BOTTOM)
        return binding.root
    }

    //khởi tạo service
    private fun createPlayingService() {
        intent = Intent(context, PlayingService::class.java)
        bundle.putInt("number1", binding.edtNumber1.text.toString().toInt())
        bundle.putInt("number2", binding.edtNumber2.text.toString().toInt())
        intent.putExtras(bundle)
        activity?.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //các funtion phép tính
        binding.btnPlus.setOnClickListener() {
            createPlayingService()
            if (isBound) {

                val result = playingService.plus()
                binding.tvResult.text = result.toString()
            }
        }
        binding.btnMinus.setOnClickListener() {
            createPlayingService()
            if (isBound) {
                val result = playingService.minus()
                binding.tvResult.text = result.toString()
            }
        }
        binding.btnMulti.setOnClickListener() {
            createPlayingService()
            if (isBound) {
                val result = playingService.multi()
                binding.tvResult.text = result.toString()
            }
        }
        binding.btnDivide.setOnClickListener() {
            createPlayingService()
            if (isBound) {
                val result = playingService.divide()
                binding.tvResult.text = result.toString()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.unbindService(serviceConnection)
    }
}