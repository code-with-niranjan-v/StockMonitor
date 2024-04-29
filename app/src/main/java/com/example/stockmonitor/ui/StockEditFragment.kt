package com.example.stockmonitor.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.stockmonitor.R
import com.example.stockmonitor.databinding.FragmentStockEditBinding
import com.example.stockmonitor.model.StockUrl
import com.example.stockmonitor.viewmodel.StockViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StockEditFragment : Fragment() {

    private lateinit var stockEditBinding: FragmentStockEditBinding
    private val viewModel:StockViewModel by viewModels()
    private val googleFinanceUrlRegex = """^https?://www\.google\.com/finance/quote/[\w.-]+:[\w.-]+\?hl=[\w.-]+$""".toRegex()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        stockEditBinding = FragmentStockEditBinding.inflate(inflater,container,false)
        return stockEditBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        stockEditBinding.btnAdd.setOnClickListener {
            val url = stockEditBinding.etUrl.text.toString()
            if (url.isNotEmpty()){
                if (isValidGoogleFinanceUrl(url)){
                    val stockUrl = StockUrl(url)
                    viewModel.insertStock(stockUrl)
                    Toast.makeText(requireContext(),"Stock Added",Toast.LENGTH_SHORT).show()
                    stockEditBinding.etUrl.text.clear()
                }else{
                    Toast.makeText(requireContext(),"Enter a Valid Url",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(requireContext(),"Enter the Url",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValidGoogleFinanceUrl(url: String): Boolean {
        return googleFinanceUrlRegex.matches(url)
    }

}