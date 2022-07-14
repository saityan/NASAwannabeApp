package ru.geekbrains.nasawannabeapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.geekbrains.nasawannabeapp.R
import ru.geekbrains.nasawannabeapp.fragments.PODFragment
import ru.geekbrains.nasawannabeapp.utils.EARTH
import ru.geekbrains.nasawannabeapp.utils.MARS

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        when (getCustomTheme()) {
            EARTH -> setTheme(R.style.Theme_NASAwannabeApp)
            MARS -> setTheme(R.style.Theme_NASAwannabeApp_Auxiliary)
        }
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PODFragment.newInstance())
                .commitNow()
        }
    }

    private fun getCustomTheme() : Int {
        return getSharedPreferences(R.string.app_name.toString(), MODE_PRIVATE)
            .getInt("customThemeID", EARTH)
    }
}
