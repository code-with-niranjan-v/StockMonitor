package com.example.stockmonitor.data.remote

import android.util.Log
import com.example.stockmonitor.model.Stock
import com.example.stockmonitor.model.StockUrl
import org.jsoup.Jsoup

class StockLoader {

    val listOfStocks = mutableListOf<Stock>()


    fun loadStock(stockUrls: List<StockUrl>){
        for (url in stockUrls){
            val html = Jsoup.connect(url.url).get()
            val name = html.getElementsByClass("zzDege")?.text()
            val value = html.getElementsByClass("YMlKec fxKbKc")?.text()
            Log.e("stock",value.toString())
            if (!name.isNullOrEmpty() && !value.isNullOrEmpty()){
                val stock = Stock(name,value)
                listOfStocks.add(stock)

            }
        }
    }

}