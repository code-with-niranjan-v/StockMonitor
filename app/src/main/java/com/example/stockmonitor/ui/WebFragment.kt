package com.example.stockmonitor.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.stockmonitor.databinding.FragmentWebBinding


class WebFragment : Fragment() {

    private lateinit var webBinding: FragmentWebBinding
    private val urlArgs: WebFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        webBinding = FragmentWebBinding.inflate(inflater,container,false)
        return webBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        if (!urlArgs.url.toString().isNullOrEmpty()){
            webBinding.webView.loadUrl(urlArgs.url.toString())

            webBinding.webView.settings.javaScriptEnabled = true

            webBinding.webView.webViewClient = WebViewClient()
        }
    }


}