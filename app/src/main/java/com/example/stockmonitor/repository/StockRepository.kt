package com.example.stockmonitor.repository

import android.util.Log
import com.example.stockmonitor.data.local.StockDatabase
import com.example.stockmonitor.data.remote.StockLoader
import com.example.stockmonitor.model.Stock
import com.example.stockmonitor.model.StockUrl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class StockRepository @Inject constructor(
    val db:StockDatabase,
    val stockLoader: StockLoader
) {


    private var urls:List<StockUrl> = emptyList()
    private val _stockListFlow = MutableStateFlow<List<Stock>>(emptyList())
    val stockListFlow: StateFlow<List<Stock>> = _stockListFlow

    suspend fun insertStock(stockUrl: StockUrl) {
        db.getDao().insertStockData(stockUrl)

    }

    fun getAllStock() = db.getDao().getAllStocks()

    suspend fun deleteAt(id:Int) {
        db.getDao().deleteAt(id)


    }

    suspend fun generateListOfStockData() =
        flow<List<Stock>> {
            while (true) {
                Log.e("stockLoader","Running")
                val urls = getAllStock()
                stockLoader.loadStock(urls)
                emit(stockLoader.listOfStocks)
                _stockListFlow.emit(stockLoader.listOfStocks)

                //  Log.e("stockLoader",stockLoader.listOfStocks.toString())
                delay(10000)

//            stockLoader.listOfStocks.clear()


            }

        }.flowOn(Dispatchers.IO)

    suspend fun generateStockInfo(url: String) = flow<String> {
        while (true) {
            Log.e("stockLoader","Running")
            val info = stockLoader.loadAdditionalInfo(url)
            emit(info)
            Log.e("stockLoader", info.toString())
            delay(6000)
        }

    }.flowOn(Dispatchers.IO)

    suspend fun generateStockData(url: String)=
        flow<Stock> {
            while (true) {
                Log.e("stockLoader","Running")
                val stock = stockLoader.loadStock(url)
                emit(stock)
                delay(5000)


            }
        }.flowOn(Dispatchers.IO)

    private suspend fun updateListFlow(){
        val stockUrls = getAllStock()
        stockLoader.reload()
//        stockLoader.loadStock(stockUrls)
        _stockListFlow.emit(stockLoader.listOfStocks)
    }



}