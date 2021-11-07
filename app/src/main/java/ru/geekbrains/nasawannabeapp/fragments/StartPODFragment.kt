package ru.geekbrains.nasawannabeapp.fragments

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import ru.geekbrains.nasawannabeapp.R
import ru.geekbrains.nasawannabeapp.databinding.FragmentPhotoStartBinding
import ru.geekbrains.nasawannabeapp.view.viewmodel.PODViewModel
import ru.geekbrains.nasawannabeapp.view.viewmodel.PODdata


class StartPODFragment : Fragment() {

    private var _binding: FragmentPhotoStartBinding? = null
    private val binding: FragmentPhotoStartBinding
        get() {
            return _binding!!
    }

    companion object{
        fun newInstance(): StartPODFragment {
            return StartPODFragment()
        }
    }

    private val viewModel : PODViewModel by lazy {
        ViewModelProvider(this).get(PODViewModel::class.java)
    }

    override fun onCreateView (
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.getPODLiveData().observe(viewLifecycleOwner, Observer { renderPODData(it) })
        _binding = FragmentPhotoStartBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPODLiveData().observe(viewLifecycleOwner, { renderPODData(it) })
        viewModel.sendServerRequest()
        binding.imageView.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right)
            transaction.replace(R.id.container, PODFragment.newInstance())
            transaction.commit()
        }
    }

    private fun renderPODData (data: PODdata) {
        when (data) {
            is PODdata.Success -> {
                val layoutParams: ViewGroup.LayoutParams = binding.imageView.layoutParams
                layoutParams.width = 2200
                layoutParams.height = 2200
                binding.imageView.layoutParams = layoutParams
                binding.imageView.load(data.serverResponseData.url) {
                    error(R.drawable.ic_load_error_vector)
                    placeholder(R.drawable.progress_animation)
                }
            }
            is PODdata.Error -> { toast(data.error.message) }
            is PODdata.Loading -> {
                binding.imageView.load(R.drawable.progress_animation)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun Fragment.toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }
}