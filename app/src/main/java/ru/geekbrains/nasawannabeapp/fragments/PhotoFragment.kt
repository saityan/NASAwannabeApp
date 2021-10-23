package ru.geekbrains.nasawannabeapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.google.android.material.snackbar.Snackbar
import ru.geekbrains.nasawannabeapp.view.MainActivity
import ru.geekbrains.nasawannabeapp.R
import ru.geekbrains.nasawannabeapp.databinding.FragmentPhotoBinding
import ru.geekbrains.nasawannabeapp.view.AppState
import ru.geekbrains.nasawannabeapp.view.viewmodel.MainViewModel

class PhotoFragment : Fragment() {

    private var _binding: FragmentPhotoBinding? = null
    private val binding: FragmentPhotoBinding
    get() {
        return _binding!!
    }

    companion object{
        fun newInstance(): PhotoFragment {
            return PhotoFragment()
        }
    }

    lateinit var mainViewModel : MainViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainViewModel = (context as MainActivity).mainViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotoBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.getLiveData().observe(viewLifecycleOwner, { render(it) })
    }

    private fun render(appState: AppState) {
        when(appState){
            is AppState.Error ->
                Snackbar.make(binding.root, appState.error.toString(), Snackbar.LENGTH_SHORT).show()
            is AppState.Loading -> {
                binding.imageView.load(R.drawable.progress_animation)
            }
            is AppState.Success -> {
                setData(appState)
            }
        }
    }

    private fun setData(data: AppState.Success)  {
        val url = data.serverResponseData.hdurl
            binding.imageView.load(url)
    }
}