package ru.geekbrains.nasawannabeapp.view.viewmodel.recycler

import android.graphics.Color
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MotionEventCompat
import androidx.recyclerview.widget.RecyclerView
import ru.geekbrains.nasawannabeapp.databinding.ActivityRecyclerItemEarthBinding
import ru.geekbrains.nasawannabeapp.databinding.ActivityRecyclerItemHeaderBinding
import ru.geekbrains.nasawannabeapp.databinding.ActivityRecyclerItemMarsBinding
import ru.geekbrains.nasawannabeapp.utils.EARTH
import ru.geekbrains.nasawannabeapp.utils.HEADER
import ru.geekbrains.nasawannabeapp.utils.MARS
import ru.geekbrains.nasawannabeapp.view.viewmodel.ViewHolderBased

class RecyclerActivityAdapter (
    private var clickListener: RecyclerClickListener,
    private var dragListener: OnDragStartListener,
    private var data: MutableList<Pair<RecyclerData, Boolean>>
) : RecyclerView.Adapter<ViewHolderBased>(), OnItemTouchHelperAdapter {

    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 -> HEADER
            this.data[position].first.planetName == "Mars" -> MARS
            else -> EARTH
        }
    }

    fun appendItem() {
        this.data.add(Pair(createItem(), false))
        notifyItemInserted(itemCount - 1)
    }

    private fun createItem() = RecyclerData("Mars", "The Red Planet")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderBased {
        return when (viewType) {
            EARTH -> {
                val binding: ActivityRecyclerItemEarthBinding =
                    ActivityRecyclerItemEarthBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                EarthViewHolder(binding.root)
            }
            MARS -> {
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

    inner class EarthViewHolder(view: View) : ViewHolderBased(view), OnItemTouchHelperViewHolder {
        override fun bind(pair: Pair<RecyclerData, Boolean>) {
            ActivityRecyclerItemEarthBinding.bind(itemView).apply {
                descriptionTextView.text = pair.first.planetDescription
                wikiImageView.setOnClickListener {
                    clickListener.onItemClick(pair.first)
                }
            }
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.GREEN)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(0)
        }

        override fun addItem() {
            data.add(layoutPosition, Pair(createItem(), false))
            notifyItemInserted(layoutPosition)
        }

        override fun removeItem() {
            data.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }
    }

    inner class MarsViewHolder(view: View) : ViewHolderBased(view), OnItemTouchHelperViewHolder {
        override fun bind(pair: Pair<RecyclerData, Boolean>) {
            ActivityRecyclerItemMarsBinding.bind(itemView).apply {
                marsImageView.setOnClickListener {
                    clickListener.onItemClick(pair.first)
                }
                addItemImageView.setOnClickListener {
                    addItem()
                }
                removeItemImageView.setOnClickListener {
                    removeItem()
                }
                moveItemUp.setOnClickListener {
                    moveUp()
                }
                moveItemDown.setOnClickListener {
                    moveDown()
                }
                marsTextView.setOnClickListener {
                    toggleText()
                }
                marsDescriptionTextView.visibility = if (pair.second) View.VISIBLE else View.GONE
                dragHandleImageView.setOnTouchListener { _, event ->
                    if(MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                        dragListener.onDragStart(this@MarsViewHolder)
                    }
                    false
                }
            }
        }

        private fun toggleText() {
            data[layoutPosition] = data[layoutPosition].let {
                it.first to !it.second
            }
            notifyItemChanged(layoutPosition)
        }

        private fun moveUp() {
            layoutPosition.takeIf{ it > 1}?.also {
                data.removeAt(it).apply {
                    data.add(it - 1, this)
                }
                notifyItemMoved(it, it - 1)
            }
        }

        private fun moveDown() {
            layoutPosition.takeIf{ it > 0 && it < itemCount - 1}?.also {
                data.removeAt(it).apply {
                    data.add(it + 1, this)
                }
                notifyItemMoved(it, it + 1)
            }
        }

        override fun addItem() {
            data.add(layoutPosition + 1, Pair(createItem(), false))
            notifyItemInserted(layoutPosition + 1)
        }

        override fun removeItem() {
            data.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(0)
        }
    }

    inner class HeaderViewHolder(view: View) : ViewHolderBased(view) {
        override fun bind(pair: Pair<RecyclerData, Boolean>) {
            ActivityRecyclerItemHeaderBinding.bind(itemView).apply {
                root.setOnClickListener {
                    clickListener.onItemClick(pair.first)
                }
            }
        }
        override fun addItem() {}
        override fun removeItem() {}
    }

    override fun onItemMove(startPosition: Int, endPosition: Int) {
        data.removeAt(startPosition).apply {
            when {
                endPosition > startPosition -> {
                    data.add(endPosition, this)
                }
                endPosition != 0 -> {
                    data.add(endPosition, this)
                }
                else -> {
                    data.add(endPosition + 1, this)
                }
            }
        }
        notifyItemMoved(startPosition, endPosition)
    }

    override fun onItemDismiss(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }

    interface OnDragStartListener {
        fun onDragStart(viewHolder: RecyclerView.ViewHolder)
    }
}
