package ru.geekbrains.nasawannabeapp.view

import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.animation.AnticipateOvershootInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import ru.geekbrains.nasawannabeapp.R
import ru.geekbrains.nasawannabeapp.databinding.ActivityAnimationsBonusStartBinding

class AnimationsActivityBonus : AppCompatActivity() {

    var show = false
    lateinit var binding: ActivityAnimationsBonusStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAnimationsBonusStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backgroundImage.setOnClickListener {
            show = !show
            val constraintSet = ConstraintSet()
            val transition = ChangeBounds()
            if (show) {
                constraintSet.clone(this, R.layout.activity_animations_bonus_end)
                transition.interpolator = AnticipateOvershootInterpolator(2.0f)
                transition.duration = 1000

            } else {
                constraintSet.clone(this, R.layout.activity_animations_bonus_start)
                transition.interpolator = AnticipateOvershootInterpolator(2.0f)
                transition.duration = 1000
            }
            TransitionManager.beginDelayedTransition(binding.constraintContainer, transition)
            constraintSet.applyTo(binding.constraintContainer)
        }
    }
}
