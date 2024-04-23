package com.example.stockmonitor.model

import android.graphics.Color
import androidx.core.graphics.toColor
import com.example.stockmonitor.R

data class Stock(
    val name:String,
    val currentValue:String,
    val url:String = "",
    val color:Int = R.color.grey,
    val id:Int = 0
)
