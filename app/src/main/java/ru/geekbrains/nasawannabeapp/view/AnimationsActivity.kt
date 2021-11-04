package ru.geekbrains.nasawannabeapp.view

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import ru.geekbrains.nasawannabeapp.databinding.ActivityAnimationsEnlargeBinding

class AnimationsActivity : AppCompatActivity() {

    lateinit var binding: ActivityAnimationsEnlargeBinding

    var isExpanded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimationsEnlargeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imageView.setOnClickListener{
            isExpanded = !isExpanded

            val set = TransitionSet()
                .addTransition(ChangeBounds())
                .addTransition(ChangeImageTransform())
            TransitionManager.beginDelayedTransition(binding.container, set)

            binding.imageView.scaleType = if(isExpanded) {
                ImageView.ScaleType.CENTER_CROP
            } else {
                ImageView.ScaleType.FIT_CENTER
            }
        }
    }
}