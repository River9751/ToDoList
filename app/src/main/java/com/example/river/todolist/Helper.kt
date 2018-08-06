package com.example.river.todolist

import android.content.Context
import android.graphics.Paint
import android.widget.TextView



//Kotlin 中使用 Singleton 的兩種方法
object Singleton1 {
    fun myFunc() {

    }
}
//使用方式：Singleton1.myFunc()


class Singleton2 private constructor() {
    companion object {
        val instance: Singleton2 by lazy { Singleton2() }
    }

    fun myFunc() {

    }
}
//使用方式：Singleton2.instance.myFunc()


//object Helper {
//    //SharedPreferences
//    lateinit var toDoItemPreferences: ToDoItemPreferences
//    lateinit var mSQLiteManager: SQLiteDBManager
//
//    fun doStrike(checked: Boolean, textView: TextView) {
//        if (checked) {
//            textView.setTextColor(android.graphics.Color.GRAY)
//            textView.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
//        } else {
//            textView.setTextColor(android.graphics.Color.BLACK)
//            textView.paint.flags = Paint.ANTI_ALIAS_FLAG
//        }
//    }
//}

class Helper(ctx: Context) {
    //SharedPreferences
     var toDoItemPreferences: ToDoItemPreferences
     var mSQLiteManager: SQLiteDBManager

    init {
        toDoItemPreferences = ToDoItemPreferences(ctx)
        mSQLiteManager = SQLiteDBManager(ctx)
    }

    fun doStrike(checked: Boolean, textView: TextView) {
        if (checked) {
            textView.setTextColor(android.graphics.Color.GRAY)
            textView.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            textView.setTextColor(android.graphics.Color.BLACK)
            textView.paint.flags = Paint.ANTI_ALIAS_FLAG
        }
    }

    fun getDataList(ctx: Context, dataSource: dataSource): MutableList<ToDoItem> {
        when (dataSource) {
            Helper.dataSource.SharedPreferences -> {
                toDoItemPreferences = ToDoItemPreferences(ctx)
                return toDoItemPreferences.getAll()
            }
            Helper.dataSource.SQLite -> {
                mSQLiteManager = SQLiteDBManager(ctx)
                return mSQLiteManager.getAll()
            }
        }
        return ArrayList()
    }

    enum class dataSource {
        SharedPreferences,
        SQLite
    }
}