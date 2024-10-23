package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemBinding

class TuDienAdapter : RecyclerView.Adapter<TuDienAdapter.TuDienViewHolder>() {

    var list = mutableListOf<TuDien>()
    fun setData(list: MutableList<TuDien>){
        this.list = list
        notifyDataSetChanged()
    }

    class TuDienViewHolder( val itemBinding: ItemBinding) : RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TuDienViewHolder {
        val itemBinding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TuDienViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TuDienViewHolder, position: Int) {
        val user = list[position]
        holder.itemBinding.tvEnglish.text = user.english
        holder.itemBinding.tvVietnamese.text = user.vietnamese
    }
}