package com.patriciafiona.bookstore.ui

import android.app.Application
import com.patriciafiona.bookstore.di.AppContainer
import com.patriciafiona.bookstore.di.DefaultAppContainer

class BookStoreApplication: Application() {
    /** AppContainer instance used by the rest of classes to obtain dependencies */
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}