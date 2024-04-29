package com.example.stockmonitor.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.stockmonitor.model.StockUrl
import kotlinx.coroutines.flow.Flow

@Dao
interface StockDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStockData(stockData:StockUrl)

    @Query("select * from stock_data")
    fun getAllStocks(): List<StockUrl>

    @Query("delete from stock_data where id is :id ")
    fun deleteAt(id:Int)



    @Query("select * from stock_data")
    fun getAllUrls(): LiveData<List<StockUrl>>


}