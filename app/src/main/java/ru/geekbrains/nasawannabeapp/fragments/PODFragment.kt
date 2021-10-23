package ru.geekbrains.nasawannabeapp.fragments

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
import ru.geekbrains.nasawannabeapp.databinding.FragmentPhotoBinding
import ru.geekbrains.nasawannabeapp.view.viewmodel.PODdata
import ru.geekbrains.nasawannabeapp.view.viewmodel.PODViewModel

class PODFragment : Fragment() {

    private var _binding: FragmentPhotoBinding? = null
    private val binding: FragmentPhotoBinding
    get() {
        return _binding!!
    }

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
    }

    private fun renderData (data: PODdata) {
        when (data) {
            is PODdata.Success -> {
                binding.imageView.load(data.serverResponseData.url) {
                    error(R.drawable.ic_load_error_vector)
                }
            }
            is PODdata.Error -> { toast(data.error.message) }
            is PODdata.Loading -> {/*ProgressBar*/}
        }
    }

    private fun Fragment.toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }
}