package com.example.river.todolist

import android.content.Context
import java.util.*

class ToDoItemPreferences(context: Context) {

    private val preferencesName: String = "ToDoItemPreferences"
    private val myPreferences = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)


    fun setData(id: String?, str: String) {
        var uniqueID:String? = id
        if (uniqueID == null) {
            uniqueID = UUID.randomUUID().toString()
        }

        val editor = myPreferences.edit()
        editor.putString(uniqueID, str)
        editor.apply()
    }

    fun removeData(uniqueId:String){
        val editor = myPreferences.edit()
        editor.remove(uniqueId)
        editor.apply()
    }

    fun Str2ToDoItem():MutableList<ToDoItem>{
        var list:MutableList<ToDoItem> = mutableListOf()
        myPreferences.all.keys.forEach{
            val itemText:String =  myPreferences.getString(it, "default")
            list.add(ToDoItem(it, itemText, false))
        }
        return list
    }


}