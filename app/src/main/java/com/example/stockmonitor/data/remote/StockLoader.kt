package com.example.stockmonitor.data.remote

import android.graphics.Color
import android.util.Log
import androidx.core.graphics.toColor
import androidx.core.graphics.toColorInt
import com.example.stockmonitor.R
import com.example.stockmonitor.model.Stock
import com.example.stockmonitor.model.StockUrl
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class StockLoader {

    val listOfStocks = mutableListOf<Stock>()
    val prevListOfStocks:MutableList<Stock> = mutableListOf()


    fun loadStock(stockUrls: List<StockUrl>){
        setupListOfStock()
        for (urlNo in 0..stockUrls.size-1){
            try{
                val html = Jsoup.connect(stockUrls[urlNo].url).get()
                val name = html.getElementsByClass("zzDege")?.text()
                val value = html.getElementsByClass("YMlKec fxKbKc")?.text()
                var textColor = R.color.grey
                val curValue = value.toString().replace("₹", "").replace(",","").replace("$","").toDouble()
                if (!prevListOfStocks.isEmpty()){
                    val prevValue = prevListOfStocks[urlNo].currentValue.toString().replace("₹", "").replace(",","").replace("$","").toDouble()
                    if(curValue>prevValue){
                        textColor = R.color.green
                    }else if(curValue<prevValue){
                        textColor = R.color.red
                    }else{
                        textColor = R.color.grey
                    }
                }

                if (!name.isNullOrEmpty() && !value.isNullOrEmpty()){

                    val stock = Stock(name,value,stockUrls[urlNo].url,textColor, id = stockUrls[urlNo].id)
                    listOfStocks.add(stock)

                }
            }catch (e:Exception){
                Log.e("stockLoader","Error -> ${e.toString()}\n${stockUrls.toString()}\n${prevListOfStocks.toString()}")
            }
        }
    }

    fun setupListOfStock()
    {
        prevListOfStocks.clear()
        prevListOfStocks.addAll(listOfStocks)
        listOfStocks.clear()
    }

    private fun extractColorFromCSS(css: String, selector: String): String {
        val pattern = Regex("$selector\\s*\\{[^}]*color:\\s*(.*?);")
        val matchResult = pattern.find(css)
        return matchResult?.groups?.get(1)?.value ?: ""
    }

    fun getColorFromString(colorValue: String): Int {
        // Extract the RGB values from the color string
        val rgbRegex = Regex("rgb\\((\\d+),\\s*(\\d+),\\s*(\\d+)\\)")
        val matchResult = rgbRegex.find(colorValue)

        // Convert RGB values to Color instance
        val red = matchResult?.groupValues?.getOrNull(1)?.toIntOrNull() ?: 0
        val green = matchResult?.groupValues?.getOrNull(2)?.toIntOrNull() ?: 0
        val blue = matchResult?.groupValues?.getOrNull(3)?.toIntOrNull() ?: 0

        // Construct Color instance
        return Color.rgb(red, green, blue)
    }

    fun loadAdditionalInfo(url:String):String{
        val html = Jsoup.connect(url).get()
        val addDetailsName = html.select(".mfs7Fc")
        val addDetails = html.select(".P6K39c")
        val listOfDetails = mutableListOf<String>()
        val listOfDetailName = mutableListOf<String>()
        for (detail in addDetails){
            listOfDetails.add(detail.text())
        }
        for (detailName in addDetailsName){
            listOfDetailName.add(detailName.text())
        }

        var text = ""
        for (i in 0..listOfDetails.size-1){
           text += ("${listOfDetailName[i]} = ${listOfDetails[i]}\n")
        }

        return text


    }

    fun loadStock(url:String):Stock{
        try{
            val html = Jsoup.connect(url).get()
            val name = html.getElementsByClass("zzDege")?.text()
            val value = html.getElementsByClass("YMlKec fxKbKc")?.text()
            var textColor = R.color.grey

            if (!name.isNullOrEmpty() && !value.isNullOrEmpty()){

                val stock = Stock(name,value,url,textColor, )
                return stock

            }else{
                return Stock("Error","0.00","",0)
            }
        }catch (e:Exception){
            Log.e("stockLoader","Error -> ${e.toString()}\n${e.message}\n")
            return Stock("Error","0.00","",0)
        }
    }
}