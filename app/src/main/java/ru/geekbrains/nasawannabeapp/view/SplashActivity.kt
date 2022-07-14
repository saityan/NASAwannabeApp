package ru.geekbrains.nasawannabeapp.view

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.view.animation.BounceInterpolator
import androidx.appcompat.app.AppCompatActivity
import ru.geekbrains.nasawannabeapp.R
import ru.geekbrains.nasawannabeapp.databinding.ActivitySplashBinding
import ru.geekbrains.nasawannabeapp.utils.EARTH
import ru.geekbrains.nasawannabeapp.utils.MARS

class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when (getCustomTheme()) {
            EARTH -> setTheme(R.style.Theme_NASAwannabeApp)
            MARS -> setTheme(R.style.Theme_NASAwannabeApp_Auxiliary)
        }

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView.animate()
            .rotationBy(480f)
            .setInterpolator(BounceInterpolator())
            .setDuration(3000)
            .setListener(object : Animator.AnimatorListener {

                override fun onAnimationEnd(animation: Animator?) {
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    finish()
                }

                override fun onAnimationStart(animation: Animator?) {}

                override fun onAnimationCancel(animation: Animator?) {}

                override fun onAnimationRepeat(animation: Animator?) {}
            })
    }

    private fun getCustomTheme() : Int {
        return getSharedPreferences(R.string.app_name.toString(), MODE_PRIVATE)
            .getInt("customThemeID", EARTH)
    }
}
