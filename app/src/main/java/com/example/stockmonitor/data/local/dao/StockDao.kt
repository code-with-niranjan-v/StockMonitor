package com.example.stockmonitor.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.stockmonitor.model.StockUrl

@Dao
interface StockDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStockData(stockData:StockUrl)

    @Query("select * from stock_data")
    fun getAllStocks():List<StockUrl>

}