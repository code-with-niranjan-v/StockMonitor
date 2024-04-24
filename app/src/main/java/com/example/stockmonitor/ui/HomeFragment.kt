package com.example.stockmonitor.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stockmonitor.R
import com.example.stockmonitor.databinding.FragmentHomeBinding
import com.example.stockmonitor.utils.Listener
import com.example.stockmonitor.utils.StockItemsAdapter
import com.example.stockmonitor.utils.SwipeToDeleteCallback
import com.example.stockmonitor.viewmodel.StockViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class HomeFragment : Fragment(),Listener {

    private lateinit var  homeBinding:FragmentHomeBinding
    private val viewModel:StockViewModel by viewModels()
    private lateinit var job:Job
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        homeBinding = FragmentHomeBinding.inflate(layoutInflater)
        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.generateListOfStock()

        homeBinding.floatingBtn.setOnClickListener {
            val bottomSheet = BottomSheetDialog()
            bottomSheet.show(childFragmentManager, "ModalBottomSheet")
        }



        viewLifecycleOwner.lifecycleScope.launch{

            viewModel.stockListOfData.observe(viewLifecycleOwner){
                Log.e("StockLoader","Inside Main")
                if (it.isEmpty()){
                    homeBinding.progressBar.visibility = View.VISIBLE
                }else{
                    homeBinding.progressBar.visibility = View.GONE
                }
                homeBinding.recyclerView.adapter =
                    StockItemsAdapter(it, this@HomeFragment)
                homeBinding.recyclerView.layoutManager =
                    LinearLayoutManager(requireContext())


            }
            }
     }



    override fun onClick(url:String) {
        val bundle = Bundle()
        bundle.putString("url",url)
        viewModel.generateListOfStock().cancel()
        findNavController().navigate(R.id.action_homeFragment_to_stockInfoFragment,bundle)

    }

    override fun onSwipeToRemove(position: Int) {
        viewModel.removeAt(position)



    }


}