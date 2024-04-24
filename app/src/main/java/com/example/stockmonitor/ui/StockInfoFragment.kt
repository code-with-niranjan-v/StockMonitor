package com.example.stockmonitor.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.stockmonitor.R
import com.example.stockmonitor.databinding.FragmentStockInfoBinding
import com.example.stockmonitor.viewmodel.StockViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class StockInfoFragment : Fragment() {

    private lateinit var stockInfoBinding: FragmentStockInfoBinding
    private val viewModel: StockViewModel by viewModels()
    private lateinit var stockLoadingJob: Job
    private lateinit var stockInfoLoadingJob: Job
    private val urlArgs: StockInfoFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        stockInfoBinding = FragmentStockInfoBinding.inflate(layoutInflater, container, false)
        return stockInfoBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.generateStockInfo(urlArgs.url)
        viewModel.generateStockData(urlArgs.url)
        stockInfoBinding.externalBtn.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("url", urlArgs.url)
            findNavController().navigate(R.id.action_stockInfoFragment_to_webFragment, bundle)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.stockInfo.observe(viewLifecycleOwner){ stockInfo ->
                stockInfoBinding.stockInfo.text = stockInfo
            }

            viewModel.stockData.observe(viewLifecycleOwner){ stock ->
                stockInfoBinding.stockName.text = stock.name
                stockInfoBinding.stockValue.text = stock.currentValue
                if (stock.currentValue.isNotEmpty() && stock.name.isNotEmpty()) {
                    stockInfoBinding.layout.visibility = View.VISIBLE
                    stockInfoBinding.progressBar.visibility = View.GONE
                }
            }


        }

    }


}
