package ru.geekbrains.nasawannabeapp.view.viewmodel

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import ru.geekbrains.nasawannabeapp.fragments.EarthFragment
import ru.geekbrains.nasawannabeapp.fragments.MarsFragment
import ru.geekbrains.nasawannabeapp.fragments.SystemFragment

private const val EARTH = 0
private const val MARS = 1
private const val SYSTEM = 2

class ViewPagerAdapter(private val fragmentManager: FragmentManager)
    : FragmentStatePagerAdapter(fragmentManager) {

    private val fragments = arrayOf(EarthFragment(), MarsFragment(), SystemFragment())

    override fun getCount(): Int = fragments.size

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> fragments[EARTH]
            1 -> fragments[MARS]
            2 -> fragments[SYSTEM]
            else -> fragments[EARTH]
        }
    }
}