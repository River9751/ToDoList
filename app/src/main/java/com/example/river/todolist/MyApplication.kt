package com.example.river.todolist

import android.app.Application
import com.facebook.stetho.Stetho


class MyApplication:Application() {
    override fun onCreate() {
        // 啟動Stetho
        Stetho.initializeWithDefaults(this)
        // 建立 Preferences
        ToDoItemPreferences(this)

        super.onCreate()
    }
}