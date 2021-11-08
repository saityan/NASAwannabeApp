package ru.geekbrains.nasawannabeapp.view.viewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.geekbrains.nasawannabeapp.databinding.ActivityRecyclerItemEarthBinding
import ru.geekbrains.nasawannabeapp.databinding.ActivityRecyclerItemMarsBinding

class RecyclerActivityAdapter (
    private var clickListener: RecyclerClickListener,
    private var data: List<RecyclerData>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return if(this.data[position].planetDescription.isNullOrBlank()) TYPE_MARS else TYPE_EARTH
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_EARTH -> {
                val binding: ActivityRecyclerItemEarthBinding =
                    ActivityRecyclerItemEarthBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                EarthViewHolder(binding.root)
            }
            TYPE_MARS -> {
                val binding: ActivityRecyclerItemMarsBinding =
                    ActivityRecyclerItemMarsBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                MarsViewHolder(binding.root)
            }
            else -> {
                val binding: ActivityRecyclerItemMarsBinding =
                    ActivityRecyclerItemMarsBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                MarsViewHolder(binding.root)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_EARTH -> {
                (holder as EarthViewHolder).bind(data[position])
            }
            TYPE_MARS -> {
                (holder as MarsViewHolder).bind(data[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class EarthViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: RecyclerData) {
            ActivityRecyclerItemEarthBinding.bind(itemView).apply {
                descriptionTextView.text = data.planetDescription
                wikiImageView.setOnClickListener {
                    clickListener.onItemClick(data)
                }
            }
        }
    }

    inner class MarsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: RecyclerData) {
            ActivityRecyclerItemMarsBinding.bind(itemView).apply {
                marsImageView.setOnClickListener {
                    clickListener.onItemClick(data)
                }
            }
        }
    }

    companion object {
        private const val TYPE_EARTH = 0
        private const val TYPE_MARS = 1
    }
}