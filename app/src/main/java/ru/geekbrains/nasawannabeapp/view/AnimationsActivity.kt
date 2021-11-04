package ru.geekbrains.nasawannabeapp.view

import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import ru.geekbrains.nasawannabeapp.databinding.ActivityAnimationsShuffleBinding

class AnimationsActivity : AppCompatActivity() {

    lateinit var binding: ActivityAnimationsShuffleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimationsShuffleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val titles: MutableList<String> = ArrayList()
        for(i in 0..4) {
            titles.add("Item $i")
        }
        binding.button.setOnClickListener {
            TransitionManager.beginDelayedTransition(binding.transitionsContainer, ChangeBounds())
            binding.transitionsContainer.removeAllViews()
            titles.shuffle()
            for(title in titles) {
                binding.transitionsContainer.addView(TextView(this).apply {
                    text = title
                    ViewCompat.setTransitionName(this, title)
                    gravity = Gravity.CENTER_HORIZONTAL
                })
            }
        }
    }
}