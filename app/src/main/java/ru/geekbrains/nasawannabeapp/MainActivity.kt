package ru.geekbrains.nasawannabeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import ru.geekbrains.nasawannabeapp.fragments.PhotoFragment
import ru.geekbrains.nasawannabeapp.view.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    val mainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel :: class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PhotoFragment.newInstance())
                .commitNow()
        }
    }
}