package com.example.river.todolist

import android.content.Context
import android.content.SharedPreferences
import java.util.*

class ToDoItemPreferences : IDataHandler {

    private val preferencesName: String = "ToDoItemPreferences"
    private val myPreferences: SharedPreferences
    private val context: Context

    constructor(context: Context) {
        this.context = context
        myPreferences = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
    }


    override fun insert(itemText: String): String? {
        val uniqueId: String = UUID.randomUUID().toString()
        val editor = myPreferences.edit()
        return try {
            editor.putString(uniqueId, "$itemText, false")
            editor.apply()
            uniqueId
        } catch (e: Exception) {
            null
        }
    }

    override fun update(toDoItem: ToDoItem): Boolean {
        val id: String = toDoItem.uniqueId
        val text: String = toDoItem.itemText
        val checked: Boolean = toDoItem.done

        val editor = myPreferences.edit()

        return try {
            editor.putString(id, "$text,$checked")
            editor.apply()
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun delete(uniqueId: String): Boolean {
        val editor = myPreferences.edit()
        return try {
            editor.remove(uniqueId)
            editor.apply()
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun getAll(): MutableList<ToDoItem> {
        var list: MutableList<ToDoItem> = mutableListOf()
        myPreferences.all.keys.forEach {
            val text: String = myPreferences.getString(it, "default")
            val strList: List<String> = text.split(",".toRegex())
            val itemText: String = strList[0]
            val checked: Boolean = strList[1] == "true"
            list.add(ToDoItem(it, itemText, checked))
        }
        return list
    }
}