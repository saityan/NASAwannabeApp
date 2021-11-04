package ru.geekbrains.nasawannabeapp.view

import android.os.Bundle
import android.transition.ArcMotion
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.Gravity
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import ru.geekbrains.nasawannabeapp.databinding.ActivityAnimationsPathTransitionsBinding

class AnimationsActivity : AppCompatActivity() {

    lateinit var binding: ActivityAnimationsPathTransitionsBinding

    var isRight = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimationsPathTransitionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener {
            isRight = !isRight
            val changeBounds = ChangeBounds()
            changeBounds.pathMotion = ArcMotion()
            changeBounds.duration = 2000
            TransitionManager.beginDelayedTransition(binding.transitionsContainer, changeBounds)
            val params = binding.button.layoutParams as FrameLayout.LayoutParams
            if (isRight) params.gravity = Gravity.END or Gravity.BOTTOM
            else params.gravity = Gravity.START or Gravity.TOP
            binding.button.layoutParams = params
        }
    }
}