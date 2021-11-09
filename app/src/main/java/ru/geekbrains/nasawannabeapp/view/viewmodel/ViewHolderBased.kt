package ru.geekbrains.nasawannabeapp.view.viewmodel

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class ViewHolderBased(view: View): RecyclerView.ViewHolder(view) {
    abstract fun bind (data: RecyclerData)
    abstract fun addItem()
    abstract fun removeItem()
}