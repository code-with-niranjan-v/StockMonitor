package com.example.stockmonitor.repository

import com.example.stockmonitor.data.local.StockDatabase
import com.example.stockmonitor.model.StockUrl
import javax.inject.Inject

class StockRepository @Inject constructor(
    val db:StockDatabase
) {

    fun insertStock(stockUrl: StockUrl) = db.getDao().insertStockData(stockUrl)

    fun getAllStock() = db.getDao().getAllStocks()

}