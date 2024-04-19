package com.example.stockmonitor.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("stock_data")
data class StockUrl(
    val url:String,
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0
)
