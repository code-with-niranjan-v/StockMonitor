package com.example.stockmonitor.utils

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.stockmonitor.databinding.StockItemsBinding
import com.example.stockmonitor.model.Stock

class StockViewHolder(
    private val stockItemsBinding: StockItemsBinding
):ViewHolder(stockItemsBinding.root){
    fun bindData(stock: Stock,listener: Listener){
        stockItemsBinding.stockName.text = stock.name
        stockItemsBinding.stockValue.text = stock.currentValue
        stockItemsBinding.stockValue.setTextColor(stockItemsBinding.stockValue.resources.getColor(stock.color))
        stockItemsBinding.stockName.setOnClickListener {
            listener.onClick(stock.url)
        }
    }
}
class StockItemsAdapter(
    private val listOfStocks:List<Stock>,
    private val listener: Listener
):Adapter<StockViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val binding = StockItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return StockViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listOfStocks.size
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        holder.bindData(listOfStocks[position],listener)
    }

    fun removeAt(position: Int){
        listener.onSwipeToRemove(listOfStocks[position].id)
    }

}

interface Listener{
    fun onClick(url:String)
    fun onSwipeToRemove(position: Int)
}