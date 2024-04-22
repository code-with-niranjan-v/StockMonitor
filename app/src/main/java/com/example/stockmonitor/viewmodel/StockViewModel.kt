package com.example.stockmonitor.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockmonitor.data.remote.StockLoader
import com.example.stockmonitor.model.Stock
import com.example.stockmonitor.model.StockUrl
import com.example.stockmonitor.repository.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class StockViewModel @Inject constructor(
    private val repo:StockRepository,
    private val stockLoader: StockLoader
):ViewModel() {

    fun generateStockData() = flow<List<Stock>> {
        while (true){
            stockLoader.loadStock(repo.getAllStock())
            delay(3000)
            emit(stockLoader.listOfStocks)
            Log.e("stockLoader",stockLoader.listOfStocks.toString())
            delay(5000)

//            stockLoader.listOfStocks.clear()


        }

    }

    fun insertStock(stockUrl:StockUrl){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                repo.insertStock(stockUrl)
            }
        }
    }

    fun removeAt(id:Int){
        viewModelScope.launch(Dispatchers.IO){
            repo.deleteAt(id)
        }
    }
}