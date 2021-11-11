package ru.geekbrains.nasawannabeapp.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import ru.geekbrains.nasawannabeapp.databinding.ActivityRecyclerBinding
import ru.geekbrains.nasawannabeapp.view.viewmodel.recycler.*

class RecyclerActivity : AppCompatActivity() {

    lateinit var binding: ActivityRecyclerBinding
    lateinit var itemTouchHelper : ItemTouchHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerData : MutableList<Pair<RecyclerData, Boolean>> = ArrayList()

        repeat(3) {
                recyclerData.add(Pair(RecyclerData("Mars", "Mars"), false))
        }
        recyclerData.add(0, Pair(RecyclerData("Header"), false))
        recyclerData.add(3, Pair(RecyclerData("Earth", "Our planet"), false))

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
            object: RecyclerActivityAdapter.OnDragStartListener {
                override fun onDragStart(viewHolder: RecyclerView.ViewHolder) {
                    itemTouchHelper.startDrag(viewHolder)
                }
            },
            recyclerData)
        binding.recyclerView.adapter = adapter
        binding.recyclerActivityFAB.setOnClickListener {
            adapter.appendItem()
        }
        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }
}

class ItemTouchHelperCallback(private val adapter: RecyclerActivityAdapter) :
    ItemTouchHelper.Callback() {

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        if (viewHolder.layoutPosition == 0) return 0
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(
            dragFlags,
            swipeFlags
        )
    }

    override fun onMove(
        recyclerView: RecyclerView,
        source: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return if (target.layoutPosition != 0) {
            adapter.onItemMove(source.adapterPosition, target.adapterPosition)
            true
        } else false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {
        adapter.onItemDismiss(viewHolder.adapterPosition)
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            val itemViewHolder = viewHolder as OnItemTouchHelperViewHolder
            itemViewHolder.onItemSelected()
        }
        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        val itemViewHolder = viewHolder as OnItemTouchHelperViewHolder
        itemViewHolder.onItemClear()
    }
}