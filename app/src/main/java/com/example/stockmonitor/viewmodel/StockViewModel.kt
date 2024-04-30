package com.example.stockmonitor.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockmonitor.data.remote.StockLoader
import com.example.stockmonitor.model.Stock
import com.example.stockmonitor.model.StockUrl
import com.example.stockmonitor.repository.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class StockViewModel @Inject constructor(
    private val repo:StockRepository,
    private val stockLoader: StockLoader
):ViewModel() {


    private val _stockListOfData = MutableLiveData<List<Stock>>(emptyList<Stock>())
    val stockListOfData:LiveData<List<Stock>> = _stockListOfData

    private val _stockInfo = MutableLiveData<String>()
    val stockInfo:LiveData<String> = _stockInfo

    private val _stockData = MutableLiveData<Stock>()
    val stockData:LiveData<Stock> = _stockData



    private lateinit var job:Job

    private val _widgetStock:MutableLiveData<Stock> = MutableLiveData()
    val widgetStock:LiveData<Stock> = _widgetStock

    fun cancelJob(){
        job.cancel()
    }


    fun insertStock(stockUrl: StockUrl) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repo.insertStock(stockUrl)
            }



        }
    }

    fun removeAt(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteAt(id)
        }



    }





    fun generateListOfStock() {
        job = viewModelScope.launch {
            repo.generateListOfStockData().collectLatest {
                _stockListOfData.value = it
            }
        }
    }


    fun generateStockInfo(url: String){
        viewModelScope.launch {
            repo.generateStockInfo(url).collect{
                _stockInfo.value = it
            }
        }
    }

    fun generateStockData(url: String){
        viewModelScope.launch{
            repo.generateStockData(url).collect{
                _stockData.value = it
            }
        }
    }

    fun delete(stockUrl: StockUrl) = viewModelScope.launch(Dispatchers.IO){
        repo.delete(stockUrl)
    }

    fun getAllUrls() = repo.getAllUrls()

    fun loadStock(url: String){
        Log.e("stockLoader","Widget Getting data")
        _widgetStock.value = stockLoader.loadStock(url)

    }


}
