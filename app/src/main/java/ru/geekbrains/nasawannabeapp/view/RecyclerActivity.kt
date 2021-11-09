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

        val recyclerData : MutableList<RecyclerData> = ArrayList()

        repeat(10) {
            if (it % 2 == 0) {
                recyclerData.add(RecyclerData("Earth"))
            } else {
                recyclerData.add(RecyclerData("Mars", "Mars"))
            }
        }
        recyclerData.add(0, RecyclerData("Header"))
        val adapter = RecyclerActivityAdapter(
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
        binding.recyclerView.adapter = adapter
        binding.recyclerActivityFAB.setOnClickListener {
            adapter.appendItem()
        }
    }
}