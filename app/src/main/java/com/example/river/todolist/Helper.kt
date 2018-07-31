package com.example.river.todolist

import android.graphics.Paint
import android.widget.TextView


//Kotlin 中使用 Singleton 的兩種方法

object Singleton1{
    fun myFunc(){

    }
}
//Singleton1.myFunc()


class Singleton2 private constructor() {
    companion object {
        val instance: Singleton2 by lazy { Singleton2() }
    }
    fun myFunc(){

    }
}
//Singleton2.instance.myFunc()



object Helper {

    fun doStrike(checked: Boolean, textView: TextView) {
        if (checked) {
            textView.setTextColor(android.graphics.Color.GRAY)
            textView.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            textView.setTextColor(android.graphics.Color.BLACK)
            textView.paint.flags = Paint.ANTI_ALIAS_FLAG
        }
    }
}