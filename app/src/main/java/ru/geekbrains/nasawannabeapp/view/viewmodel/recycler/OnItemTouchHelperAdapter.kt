package ru.geekbrains.nasawannabeapp.view.viewmodel.recycler

interface OnItemTouchHelperAdapter {
    fun onItemMove(startPosition: Int, endPosition: Int)
    fun onItemDismiss(position: Int)
}

interface OnItemTouchHelperViewHolder {
    fun onItemSelected()
    fun onItemClear()
}