package ru.geekbrains.nasawannabeapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import ru.geekbrains.nasawannabeapp.databinding.FragmentSettingsBinding

class SettingsFragment:Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding: FragmentSettingsBinding
        get() {
            return _binding!!
        }

    companion object {
        fun newInstance() = SettingsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =  FragmentSettingsBinding.inflate(inflater)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*binding.includeChips.chipGroup.setOnCheckedChangeListener{ childGroup, position ->
            Toast.makeText(context,"Click $position",Toast.LENGTH_SHORT).show()
        }*/
        binding.includeChips.chipWithClose.setOnCloseIconClickListener {
            Toast.makeText(context,"Click on chipWithClose",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}