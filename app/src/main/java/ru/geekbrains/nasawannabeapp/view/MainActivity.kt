package ru.geekbrains.nasawannabeapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.geekbrains.nasawannabeapp.R
import ru.geekbrains.nasawannabeapp.fragments.PODFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PODFragment.newInstance())
                .commitNow()
        }
    }
}