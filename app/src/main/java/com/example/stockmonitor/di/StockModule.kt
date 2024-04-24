package com.example.stockmonitor.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.stockmonitor.data.local.StockDatabase
import com.example.stockmonitor.data.remote.StockLoader
import com.example.stockmonitor.repository.StockRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StockModule {

    @Provides
    @Singleton
    fun provideDb(@ApplicationContext app:Context):StockDatabase = Room.databaseBuilder(app,StockDatabase::class.java,"stock_db").build()



    @Provides
    @Singleton
    fun provideStockLoader() = StockLoader()

    @Provides
    @Singleton
    fun provideRepo(db:StockDatabase,stockLoader: StockLoader) = StockRepository(db,stockLoader)

}