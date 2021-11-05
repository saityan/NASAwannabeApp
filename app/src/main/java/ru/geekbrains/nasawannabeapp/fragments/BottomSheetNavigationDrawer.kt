package ru.geekbrains.nasawannabeapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.geekbrains.nasawannabeapp.view.AnimationsActivityBonus
import ru.geekbrains.nasawannabeapp.R
import ru.geekbrains.nasawannabeapp.databinding.BottomNavigationLayoutBinding
import ru.geekbrains.nasawannabeapp.view.AnimationsActivity

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    private var _binding: BottomNavigationLayoutBinding? = null
    val binding: BottomNavigationLayoutBinding
    get() = _binding!!

    companion object {
        fun newInstance() = BottomNavigationDrawerFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomNavigationLayoutBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.navigationView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.navigation_one -> {
                    activity?.let {
                        startActivity(Intent(it, AnimationsActivity::class.java))
                    }
                }
                R.id.navigation_two -> {
                    activity?.let {
                        startActivity(Intent(it, AnimationsActivityBonus::class.java))
                    }
                }
            }
            true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
