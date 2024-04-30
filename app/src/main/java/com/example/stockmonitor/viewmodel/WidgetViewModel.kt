package com.example.stockmonitor.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockmonitor.data.remote.StockLoader
import com.example.stockmonitor.model.Stock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WidgetViewModel(
    private val stockLoader: StockLoader
):ViewModel() {

    private val _widgetStock: MutableLiveData<Stock> = MutableLiveData()
    val widgetStock: LiveData<Stock> = _widgetStock


    fun loadStock(url: String){
       viewModelScope.launch(Dispatchers.IO){
           Log.e("stockLoader","Widget Getting data")
           val data = stockLoader.loadStock(url)
           withContext(Dispatchers.Main){
               _widgetStock.value = data
           }
           Log.e("stockLoader","Widget Getting data ${data}")
       }

    }


}