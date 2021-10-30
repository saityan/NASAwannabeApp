package ru.geekbrains.nasawannabeapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.geekbrains.nasawannabeapp.databinding.ActivityApiBinding
import ru.geekbrains.nasawannabeapp.view.viewmodel.ViewPagerAdapter

class ApiActivity : AppCompatActivity() {

    lateinit var binding : ActivityApiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewPager.adapter = ViewPagerAdapter(supportFragmentManager)
    }
}