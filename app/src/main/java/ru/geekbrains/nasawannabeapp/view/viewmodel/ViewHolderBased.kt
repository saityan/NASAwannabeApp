package ru.geekbrains.nasawannabeapp.view.viewmodel

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.geekbrains.nasawannabeapp.view.viewmodel.recycler.RecyclerData

abstract class ViewHolderBased(view: View): RecyclerView.ViewHolder(view) {
    abstract fun bind (pair: Pair<RecyclerData, Boolean>)
    abstract fun addItem()
    abstract fun removeItem()
}
