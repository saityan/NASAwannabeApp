package ru.geekbrains.nasawannabeapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.geekbrains.nasawannabeapp.R
import ru.geekbrains.nasawannabeapp.databinding.ActivityApiBinding
import ru.geekbrains.nasawannabeapp.utils.*
import ru.geekbrains.nasawannabeapp.view.viewmodel.ViewPagerAdapter

class ApiActivity : AppCompatActivity() {

    lateinit var binding : ActivityApiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewPager.adapter = ViewPagerAdapter(supportFragmentManager)
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        binding.tabLayout.getTabAt(EARTH)?.customView = layoutInflater.inflate(
            R.layout.activity_api_tablelayout_earth, null)
        binding.tabLayout.getTabAt(MARS)?.customView = layoutInflater.inflate(
            R.layout.activity_api_tablelayout_mars, null)
        binding.tabLayout.getTabAt(SYSTEM)?.customView = layoutInflater.inflate(
            R.layout.activity_api_tablelayout_system, null)
    }
}