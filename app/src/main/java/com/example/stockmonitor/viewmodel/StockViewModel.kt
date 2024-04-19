package com.example.stockmonitor.viewmodel

import androidx.lifecycle.ViewModel
import com.example.stockmonitor.data.remote.StockLoader
import com.example.stockmonitor.model.Stock
import com.example.stockmonitor.model.StockUrl
import com.example.stockmonitor.repository.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class StockViewModel @Inject constructor(
    private val repo:StockRepository,
    private val stockLoader: StockLoader
):ViewModel() {

    fun generateStockData() = flow<List<Stock>> {
        for (i in 1..100){
            stockLoader.loadStock(listOf(StockUrl("https://www.google.com/finance/quote/NIFTY_50:INDEXNSE?hl=en")))

            delay(3000)

            emit(stockLoader.listOfStocks)




        }

    }

}