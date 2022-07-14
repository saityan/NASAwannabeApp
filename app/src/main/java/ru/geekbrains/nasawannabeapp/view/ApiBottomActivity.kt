package ru.geekbrains.nasawannabeapp.view

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.badge.BadgeDrawable
import ru.geekbrains.nasawannabeapp.R
import ru.geekbrains.nasawannabeapp.databinding.ActivityApiBottomBinding
import ru.geekbrains.nasawannabeapp.fragments.EarthFragment
import ru.geekbrains.nasawannabeapp.fragments.MarsFragment
import ru.geekbrains.nasawannabeapp.fragments.SystemFragment
import ru.geekbrains.nasawannabeapp.utils.EARTH
import ru.geekbrains.nasawannabeapp.utils.MARS

class ApiBottomActivity : AppCompatActivity() {
    lateinit var binding : ActivityApiBottomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when (getCustomTheme()) {
            EARTH -> setTheme(R.style.Theme_NASAwannabeApp)
            MARS -> setTheme(R.style.Theme_NASAwannabeApp_Auxiliary)
        }

        binding = ActivityApiBottomBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.actionEarth -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.containerBottom, EarthFragment())
                        .commit()
                    true
                }
                R.id.actionMars -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.containerBottom, MarsFragment())
                        .commit()
                    true
                }
                R.id.actionSystem -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.containerBottom, SystemFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }
        binding.bottomNavigationView.selectedItemId = R.id.actionMars
        binding.bottomNavigationView.getOrCreateBadge(R.id.actionEarth).apply {
            number = 100
            badgeGravity = BadgeDrawable.TOP_END
            maxCharacterCount = 3
        }
        binding.bottomNavigationView.menu.getItem(MARS).apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                tooltipText = "This is Mars"
            }
        }
    }

    private fun getCustomTheme() : Int {
        return getSharedPreferences(R.string.app_name.toString(), MODE_PRIVATE)
            .getInt("customThemeID", EARTH)
    }
}
