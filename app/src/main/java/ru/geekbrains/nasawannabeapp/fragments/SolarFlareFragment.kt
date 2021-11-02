package ru.geekbrains.nasawannabeapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.android.material.bottomappbar.BottomAppBar
import ru.geekbrains.nasawannabeapp.R
import ru.geekbrains.nasawannabeapp.databinding.FragmentSolarFlareBinding
import ru.geekbrains.nasawannabeapp.utils.EARTH
import ru.geekbrains.nasawannabeapp.utils.MARS
import ru.geekbrains.nasawannabeapp.view.ApiActivity
import ru.geekbrains.nasawannabeapp.view.ApiBottomActivity
import ru.geekbrains.nasawannabeapp.view.MainActivity
import ru.geekbrains.nasawannabeapp.view.viewmodel.SolarFlareData
import ru.geekbrains.nasawannabeapp.view.viewmodel.SolarFlareViewModel

class SolarFlareFragment : Fragment() {

    private var _binding: FragmentSolarFlareBinding? = null
    private val binding: FragmentSolarFlareBinding
        get() {
            return _binding!!
        }

    private var isMain = true

    companion object{
        fun newInstance(): SolarFlareFragment {
            return SolarFlareFragment()
        }
    }

    private val viewModel : SolarFlareViewModel by lazy {
        ViewModelProvider(this).get(SolarFlareViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        savedInstanceState?.let {
            isMain = it.getBoolean("isMain")
        }
        viewModel.getSolarFlareLiveData().observe(viewLifecycleOwner, Observer { renderSolarFlareData(it) })
        _binding = FragmentSolarFlareBinding.inflate(inflater)
        setActionBar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getSolarFlareLiveData().observe(viewLifecycleOwner, { renderSolarFlareData(it) })
        viewModel.sendServerRequest()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        if (!isMain) inflater.inflate(R.menu.menu_bottom_bar_other_screen, menu)
        else inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_api_activity -> {
                startActivity(Intent(context, ApiActivity::class.java))
            }
            R.id.action_api_bottom_activity -> {
                startActivity(Intent(context, ApiBottomActivity::class.java))
            }
            R.id.app_bar_settings -> {
                requireActivity()
                    .supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, SettingsFragment.newInstance())
                    .addToBackStack("")
                    .commit()
            }
            R.id.app_bar_theme -> {
                val preferences = requireActivity().getSharedPreferences(
                    R.string.app_name.toString(), AppCompatActivity.MODE_PRIVATE
                )
                when (preferences.getInt("customThemeID", EARTH)) {
                    EARTH -> preferences.edit().putInt("customThemeID", MARS).apply()
                    MARS -> preferences.edit().putInt("customThemeID", EARTH).apply()
                }
                activity?.recreate()
            }
            R.id.app_bar_main -> {
                requireActivity()
                    .supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, PODFragment.newInstance())
                    .commit()
            }
            android.R.id.home -> {
                BottomNavigationDrawerFragment.newInstance()
                    .show(requireActivity().supportFragmentManager, "")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun renderSolarFlareData (data: SolarFlareData) {
        when (data) {
            is SolarFlareData.Success -> {
                binding.sourceLocationView.text =
                    getString(R.string.source_location, data.serverResponseData.sourceLocation)
                binding.classTypeView.text =
                    getString(R.string.class_type, data.serverResponseData.classType)
                binding.peakTimeView.text =
                    getString(R.string.peak_time, data.serverResponseData.peakTime)
                binding.sourceLocationView.visibility = View.VISIBLE
                binding.classTypeView.visibility = View.VISIBLE
                binding.peakTimeView.visibility = View.VISIBLE
            }
            is SolarFlareData.Error -> { toast(data.error.message) }
            is SolarFlareData.Loading -> {
                binding.sourceLocationView.visibility = View.INVISIBLE
                binding.classTypeView.visibility = View.INVISIBLE
                binding.peakTimeView.visibility = View.INVISIBLE
            }
        }
    }

    private fun setActionBar() {
        (context as MainActivity).setSupportActionBar(binding.bottomAppBar)
        setHasOptionsMenu(true)
        if(!isMain) {
            binding.bottomAppBar.navigationIcon = null
            binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
            binding.fab.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_back_fab
                )
            )
            binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar_other_screen)
        }
        binding.fab.setOnClickListener {
            if (isMain) {
                isMain = false
                binding.bottomAppBar.navigationIcon = null
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                binding.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_back_fab
                    )
                )
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar_other_screen)
            } else {
                isMain = true
                binding.bottomAppBar.navigationIcon =
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_hamburger_menu_bottom_bar
                    )
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                binding.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_plus_fab
                    )
                )
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar)
            }
        }
    }

    private fun Fragment.toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}