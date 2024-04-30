package com.example.stockmonitor.utils

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.RemoteViews
import com.example.stockmonitor.R
import com.example.stockmonitor.data.remote.StockLoader
import com.example.stockmonitor.viewmodel.WidgetViewModel


class StockWidget : AppWidgetProvider() {
    private val viewModel = WidgetViewModel(StockLoader())
    private val handler = Handler(Looper.getMainLooper())
    private val updateInterval = 5000L // 5 seconds

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        // Initialize the ViewModel and start periodic updates
        viewModel.loadStock("https://www.google.com/finance/quote/.DJI:INDEXDJX?hl=en")
        startPeriodicUpdates(context, appWidgetManager, appWidgetIds)
    }

    private fun startPeriodicUpdates(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        if (context == null || appWidgetManager == null || appWidgetIds == null) return

        val updateRunnable = object : Runnable {
            override fun run() {
                // Reload stock data and update widgets

                appWidgetIds.forEach { appWidgetId ->
                    updateAppWidget(context, appWidgetManager, appWidgetId)
                }

                // Schedule the next update
                handler.postDelayed(this, updateInterval)
            }
        }

        // Start the periodic update
        handler.postDelayed(updateRunnable, updateInterval)
    }

    private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        val remoteViews = RemoteViews(context.packageName, R.layout.widget_layout)
        viewModel.loadStock("https://www.google.com/finance/quote/.DJI:INDEXDJX?hl=en")
        viewModel.widgetStock.observeForever { stockData ->
            if (stockData != null) {
                remoteViews.setTextViewText(R.id.stockName, stockData.name ?: "Loading")
                remoteViews.setTextViewText(R.id.stockValue, stockData.currentValue ?: "Loading")
            } else {
                remoteViews.setTextViewText(R.id.stockName, "Loading")
                remoteViews.setTextViewText(R.id.stockValue, "Loading")
            }

            // Update the widget
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
        }
    }

    override fun onDisabled(context: Context?) {
        // Stop periodic updates when the widget is disabled
        handler.removeCallbacksAndMessages(null)
    }
}
