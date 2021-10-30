package ru.geekbrains.nasawannabeapp.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ru.geekbrains.nasawannabeapp.R
import ru.geekbrains.nasawannabeapp.databinding.FragmentPhotoBinding
import ru.geekbrains.nasawannabeapp.view.ApiActivity
import ru.geekbrains.nasawannabeapp.view.ApiBottomActivity
import ru.geekbrains.nasawannabeapp.view.MainActivity
import ru.geekbrains.nasawannabeapp.view.viewmodel.PODdata
import ru.geekbrains.nasawannabeapp.view.viewmodel.PODViewModel

class PODFragment : Fragment() {

    private lateinit var bottomSheetBehaviour: BottomSheetBehavior<ConstraintLayout>

    private var _binding: FragmentPhotoBinding? = null
    private val binding: FragmentPhotoBinding
    get() {
        return _binding!!
    }

    private var isMain = true

    companion object{
        fun newInstance(): PODFragment {
            return PODFragment()
        }
    }

    private val viewModel : PODViewModel by lazy {
        ViewModelProvider(this).get(PODViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        _binding = FragmentPhotoBinding.inflate(inflater)
        setActionBar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
        viewModel.sendServerRequest()
        binding.inputLayout.setEndIconOnClickListener {
            val intent = Intent (Intent.ACTION_VIEW).apply {
                data = Uri.parse(
                    "https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            }
            startActivity(intent)
        }

        bottomSheetBehaviour = BottomSheetBehavior.from(binding.includeLayout.bottomSheetContainer)
        bottomSheetBehaviour.isHideable = false
        bottomSheetBehaviour.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
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
                when (preferences.getInt("customThemeID", 0)) {
                    0 -> preferences.edit().putInt("customThemeID", 1).apply()
                    1 -> preferences.edit().putInt("customThemeID", 0).apply()
                }
                activity?.recreate()
            }
            android.R.id.home -> {
                BottomNavigationDrawerFragment.newInstance()
                    .show(requireActivity().supportFragmentManager, "")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setActionBar() {
        (context as MainActivity).setSupportActionBar(binding.bottomAppBar)
        setHasOptionsMenu(true)
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

    private fun renderData (data: PODdata) {
        when (data) {
            is PODdata.Success -> {
                binding.imageView.load(data.serverResponseData.url) {
                    error(R.drawable.ic_load_error_vector)
                }
                binding.includeLayout.bottomSheetDescriptionHeader.text = data.serverResponseData.title
                binding.includeLayout.bottomSheetDescription.text = data.serverResponseData.explanation
            }
            is PODdata.Error -> { toast(data.error.message) }
            is PODdata.Loading -> { /*TODO "progress bar"*/ }
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