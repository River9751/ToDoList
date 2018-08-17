package com.example.river.todolist

import android.app.Application
import android.support.multidex.MultiDexApplication
import com.example.river.todolist.helper.ToDoItemPreferences
import com.facebook.stetho.Stetho


class MyApplication : MultiDexApplication() {


    override fun onCreate() {
        // 啟動Stetho
        Stetho.initializeWithDefaults(this)
        // 建立 Preferences
        ToDoItemPreferences(this)

        super.onCreate()
    }


}