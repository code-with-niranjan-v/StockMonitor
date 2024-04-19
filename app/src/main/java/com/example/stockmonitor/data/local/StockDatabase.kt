package com.example.stockmonitor.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.stockmonitor.data.local.dao.StockDao
import com.example.stockmonitor.model.StockUrl


@Database(entities = [StockUrl::class], version = 1)
abstract class StockDatabase: RoomDatabase() {

    abstract fun getDao():StockDao

}