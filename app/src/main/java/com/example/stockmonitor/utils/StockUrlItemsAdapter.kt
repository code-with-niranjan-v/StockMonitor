package com.example.stockmonitor.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.stockmonitor.databinding.StockUrlItemsBinding
import com.example.stockmonitor.model.StockUrl

class StockUrlViewHolder(private var binding: StockUrlItemsBinding):ViewHolder(binding.root){
    fun bindData(data:StockUrl,listener: StockUrlListener){
        binding.tvUrlLink.text = data.url.toString()
        binding.imgDelete.setOnClickListener {
            listener.onClick(data)
        }
    }
}

class StockUrlItemsAdapter(
    private val list: List<StockUrl>,
    private val listener: StockUrlListener
):Adapter<StockUrlViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockUrlViewHolder {
        val binding = StockUrlItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return StockUrlViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: StockUrlViewHolder, position: Int) {
        holder.bindData(list[position],listener)
    }


}


interface StockUrlListener{
    fun onClick(stockUrl: StockUrl)
}
