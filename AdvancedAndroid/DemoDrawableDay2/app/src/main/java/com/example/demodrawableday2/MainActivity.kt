package com.example.demodrawableday2

import android.graphics.BitmapFactory
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ClipDrawable
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.demodrawableday2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //binding.imgLogo.setImageDrawable(resources.getDrawable(R.drawable.kotlin_logo))
        //1. bitmat drawble
        binding.btnImageDrawable.setOnClickListener() {
            val bitmapDrawable = BitmapFactory.decodeResource(resources, R.drawable.kotlin_logo)
            binding.img.setImageBitmap(bitmapDrawable)
        }

        //2.shape drawable
        binding.btnShape.setBackgroundResource(R.drawable.shape)

        //3. state list
        binding.btnStateList.setBackgroundResource(R.drawable.state_list)

        //4. vector Drawable @drawable/ic_replay...

        //5.animation list
        binding.cbAnimationList.setOnCheckedChangeListener { _, isCheck ->
            val animation: AnimationDrawable
            binding.img.apply {
                setImageResource(android.R.color.transparent)
                setBackgroundResource(R.drawable.animation_list)
                animation = background as AnimationDrawable
            }
            if (isCheck)
                animation.start()
            else {
                animation.stop()
                binding.img.background = resources.getDrawable(android.R.color.transparent,null)
            }
        }

        //6. level list
        binding.btnLevelList.setOnClickListener() {
            binding.img.setImageResource(R.drawable.level_list)
            binding.img.setImageLevel(binding.edtLevelList.text.toString().toInt())
        }

        //7. layer list
        binding.btnLayerList.setOnClickListener() {
            binding.img.setImageResource(R.drawable.layer_list)
        }

        //8. ripple
        binding.btnRipple.setOnClickListener() {
            binding.btnRipple.setBackgroundResource(R.drawable.ripple)
        }

        //9. transition
        val ANIMATION_TIME = 1000
        binding.cbTransition.setOnCheckedChangeListener { _, isCheck ->
            binding.img.setImageResource(R.drawable.transition)
            val lightDrawable = binding.img.drawable as TransitionDrawable
            if (isCheck)
                lightDrawable.startTransition(ANIMATION_TIME)
            else
                lightDrawable.reverseTransition(ANIMATION_TIME)
        }

        //more drawable
        //10. nine patch
        binding.tv9patch.visibility = View.GONE
        binding.cbNinePatch.setOnCheckedChangeListener { _, isCheck ->
            if (isCheck) {
                binding.tv9patch.visibility = View.VISIBLE
                binding.tv9patch.text = binding.edtLevelList.text
            } else {
                binding.tv9patch.visibility = View.GONE
            }
        }

        //11. clip drawable
        binding.btnClip.setOnClickListener() {
            binding.img.setImageResource(R.drawable.clip)
            val clipDrawable: ClipDrawable = binding.img.drawable as ClipDrawable
            clipDrawable.setLevel(clipDrawable.level + 5000)

        }

        //12. scale
        binding.btnScale.setOnClickListener() {
            binding.img.setImageResource(R.drawable.scale)
        }

        //13. inset
        binding.btnInset.setOnClickListener() {
            binding.img.setImageResource(R.drawable.inset)
        }
    }
}