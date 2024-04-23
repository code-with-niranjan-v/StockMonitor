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
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class StockViewModel @Inject constructor(
    private val repo:StockRepository,
    private val stockLoader: StockLoader
):ViewModel() {




    suspend fun generateStockData() =
        flow<List<Stock>> {
            while (true) {
                val urls = repo.getAllStock()
                stockLoader.loadStock(urls)
                emit(stockLoader.listOfStocks)

                //  Log.e("stockLoader",stockLoader.listOfStocks.toString())
                delay(10000)

//            stockLoader.listOfStocks.clear()


            }

        }


    fun insertStock(stockUrl: StockUrl) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repo.insertStock(stockUrl)
            }
        }
    }

    fun removeAt(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteAt(id)
        }
        reload()
    }

    fun reload() = stockLoader.setupListOfStock()

    suspend fun generateStock(url: String) =
        flow<Stock> {
            while (true) {
                val stock = stockLoader.loadStock(url)
                emit(stock)
                delay(5000)


            }
        }

    suspend fun generateStockInfo(url: String) = flow<String> {
        while (true) {
            val info = stockLoader.loadAdditionalInfo(url)
            emit(info)
            Log.e("stockLoader", info.toString())
            delay(6000)
        }

    }

}
