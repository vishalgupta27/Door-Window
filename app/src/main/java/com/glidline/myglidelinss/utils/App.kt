package com.glidline.myglidelinss.utils

import android.app.Application
import android.app.ProgressDialog
import android.content.Context

public class App : Application() {


    companion object {
        lateinit var instance: App

        lateinit var context: Context
       lateinit var progressDialog : ProgressDialog


    }


    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        instance = this

        //singleton = new Singleton();


    }


}