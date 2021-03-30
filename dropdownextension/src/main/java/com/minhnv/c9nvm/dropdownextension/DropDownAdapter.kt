package com.minhnv.c9nvm.dropdownextension

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

internal class DropDownAdapter<T : DropDownTemplate>(
    private val backgroundColor: Int = Color.LTGRAY,
    private val listener: OnSelectedChangeListeners,
    private val listItem: MutableList<T>
) : RecyclerView.Adapter<DropDownAdapter<T>.DropDownViewHolder<T>>() {
    private var itemHasSelected: Int = -1

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DropDownViewHolder<T> {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_dropdown, parent, false)
        return DropDownViewHolder(view)
    }

    override fun onBindViewHolder(holder: DropDownViewHolder<T>, position: Int) {
        holder.bind(listItem[position], position)
    }

    fun changeBackgroundItemSelected(backgroundColor: Int) {
        notifyDataSetChanged()
    }

    inner class DropDownViewHolder<T : DropDownTemplate>(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val tvDisplayDropDown: TextView = itemView.findViewById(R.id.tvDisplayDropDown)
        fun bind(data: T, position: Int) {
            tvDisplayDropDown.text = data.textDisplay
            if (data.isSelected) {
                tvDisplayDropDown.setBackgroundColor(backgroundColor)
            } else {
                tvDisplayDropDown.setBackgroundColor(Color.WHITE)
            }
            itemView.setOnClickListener {
                data.isSelected = !data.isSelected
                notifyItemChanged(position)
                listener.changeText(data.textDisplay, position)
                when {
                    itemHasSelected != position && itemHasSelected != -1 -> {
                        listItem[itemHasSelected].isSelected = !listItem[itemHasSelected].isSelected
                        notifyItemChanged(itemHasSelected)
                    }
                    itemHasSelected == position -> {
                        data.isSelected = !data.isSelected
                        notifyItemChanged(position)
                    }
                }
                itemHasSelected = position
            }
        }
    }
}