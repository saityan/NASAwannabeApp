package ru.geekbrains.nasawannabeapp.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ru.geekbrains.nasawannabeapp.databinding.ActivityRecyclerBinding
import ru.geekbrains.nasawannabeapp.view.viewmodel.RecyclerActivityAdapter
import ru.geekbrains.nasawannabeapp.view.viewmodel.RecyclerClickListener
import ru.geekbrains.nasawannabeapp.view.viewmodel.RecyclerData

class RecyclerActivity : AppCompatActivity() {

    lateinit var binding: ActivityRecyclerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerData = listOf(
            RecyclerData("Earth", ""),
            RecyclerData("Mars", "Mars"),
            RecyclerData("Mars", "Mars"),
            RecyclerData("Mars", "Mars"),
            RecyclerData("Earth", ""),
            RecyclerData("Earth", ""),
            RecyclerData("Mars", "Mars"),
            RecyclerData("Mars", "Mars"),
            RecyclerData("Earth", ""),
        )
        binding.recyclerView.adapter = RecyclerActivityAdapter(
            object: RecyclerClickListener {
                override fun onItemClick(data: RecyclerData) {
                    Toast.makeText(
                        this@RecyclerActivity,
                        data.planetName,
                        Toast.LENGTH_SHORT)
                        .show()
                }
            },
        recyclerData)
    }
}