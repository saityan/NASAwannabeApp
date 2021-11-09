package ru.geekbrains.nasawannabeapp.view.viewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.geekbrains.nasawannabeapp.databinding.ActivityRecyclerItemEarthBinding
import ru.geekbrains.nasawannabeapp.databinding.ActivityRecyclerItemHeaderBinding
import ru.geekbrains.nasawannabeapp.databinding.ActivityRecyclerItemMarsBinding

class RecyclerActivityAdapter (
    private var clickListener: RecyclerClickListener,
    private var data: MutableList<RecyclerData>
) : RecyclerView.Adapter<ViewHolderBased>() {

    override fun getItemViewType(position: Int): Int {
        if (position == 0) return TYPE_HEADER
        return if(this.data[position].planetDescription.isBlank()) TYPE_EARTH else TYPE_MARS
    }

    fun appendItem() {
        this.data.add(createItem())
        notifyItemInserted(itemCount - 1)
    }

    private fun createItem() = RecyclerData("Mars", "The Red Planet")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderBased {
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
                val binding: ActivityRecyclerItemHeaderBinding =
                    ActivityRecyclerItemHeaderBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                HeaderViewHolder(binding.root)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolderBased, position: Int) {
            holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class EarthViewHolder(view: View) : ViewHolderBased(view) {
        override fun bind(data: RecyclerData) {
            ActivityRecyclerItemEarthBinding.bind(itemView).apply {
                descriptionTextView.text = data.planetDescription
                wikiImageView.setOnClickListener {
                    clickListener.onItemClick(data)
                }
            }
        }

        override fun addItem() {
            data.add(layoutPosition, createItem())
            notifyItemInserted(layoutPosition)
        }

        override fun removeItem() {
            data.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }
    }

    inner class MarsViewHolder(view: View) : ViewHolderBased(view) {
        override fun bind(data: RecyclerData) {
            ActivityRecyclerItemMarsBinding.bind(itemView).apply {
                marsImageView.setOnClickListener {
                    clickListener.onItemClick(data)
                }
                addItemImageView.setOnClickListener {
                    addItem()
                }
                removeItemImageView.setOnClickListener {
                    removeItem()
                }
            }
        }

        override fun addItem() {
            data.add(layoutPosition, createItem())
            notifyItemInserted(layoutPosition)
        }

        override fun removeItem() {
            data.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }
    }

    inner class HeaderViewHolder(view: View) : ViewHolderBased(view) {
        override fun bind(data: RecyclerData) {
            ActivityRecyclerItemHeaderBinding.bind(itemView).apply {
                root.setOnClickListener {
                    clickListener.onItemClick(data)
                }
            }
        }

        override fun addItem() {
            data.add(layoutPosition, createItem())
            notifyItemInserted(layoutPosition)
        }

        override fun removeItem() {
            data.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }
    }

    companion object {
        private const val TYPE_EARTH = 0
        private const val TYPE_MARS = 1
        private const val TYPE_HEADER = -1
    }
}