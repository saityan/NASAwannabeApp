package ru.geekbrains.nasawannabeapp.view

import android.os.Bundle
import android.transition.Slide
import android.transition.TransitionManager
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import ru.geekbrains.nasawannabeapp.databinding.ActivityAnimationBinding

class AnimationsActivity : AppCompatActivity() {

    lateinit var binding: ActivityAnimationBinding
    private var isTextVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener {
            TransitionManager.beginDelayedTransition(binding.transitionsContainer, Slide(Gravity.END))
            isTextVisible = !isTextVisible
            binding.text.visibility = if (isTextVisible) View.VISIBLE else View.GONE
        }
    }
}