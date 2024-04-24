package com.example.stockmonitor.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.stockmonitor.databinding.BottomSheetBinding
import com.example.stockmonitor.model.StockUrl
import com.example.stockmonitor.viewmodel.StockViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomSheetDialog():BottomSheetDialogFragment() {

    private lateinit var bottomSheetBinding:BottomSheetBinding
    private val viewModel:StockViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bottomSheetBinding = BottomSheetBinding.inflate(inflater,container,false)

        bottomSheetBinding.btnAdd.setOnClickListener {
            if (bottomSheetBinding.etUrl.text.toString().isNotEmpty()){
                viewModel.insertStock(StockUrl(bottomSheetBinding.etUrl.text.toString()))
                bottomSheetBinding.etUrl.text.clear()
                Toast.makeText(context,"Stock Added",Toast.LENGTH_SHORT).show()
            }

        }

        return bottomSheetBinding.root
    }

}