package com.example.river.todolist.helper

import android.content.Context
import android.content.SharedPreferences
import com.example.river.todolist.IDataHandler
import com.example.river.todolist.ToDoItem
import java.util.*

class ToDoItemPreferences : IDataHandler {

    private val preferencesName: String = "ToDoItemPreferences"
    private val myPreferences: SharedPreferences
    private val context: Context

    constructor(context: Context) {
        this.context = context
        myPreferences = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
    }

    override fun load(cb: IDataHandler.Callback) {
        try {
            val list: MutableList<ToDoItem> = mutableListOf()
            myPreferences.all.keys.forEach {
                val text: String = myPreferences.getString(it, "default")!!
                val strList: List<String> = text.split(",".toRegex())
                val itemText: String = strList[0]
                val checked: Boolean = strList[1] == "true"
                list.add(ToDoItem(it, itemText, checked))
            }
            cb.onSuccess(list)
        } catch (ex: Exception) {
            cb.onError(ex.message!!)
        }
    }

    override fun insert(itemText: String, cb: IDataHandler.Callback) {
        val uniqueId: String = UUID.randomUUID().toString()
        val editor = myPreferences.edit()

        try {
            editor.putString(uniqueId, "$itemText, false")
            editor.apply()
            cb.onSuccess(ToDoItem(uniqueId, itemText, false))
        } catch (ex: Exception) {
            cb.onError(ex.message!!)
        }
    }

    override fun delete(uniqueId: String, cb: IDataHandler.Callback) {
        val editor = myPreferences.edit()
        try {
            editor.remove(uniqueId)
            editor.apply()
            cb.onSuccess(uniqueId)
        } catch (ex: Exception) {
            cb.onError(ex.message!!)
        }
    }

    override fun update(toDoItem: ToDoItem, cb: IDataHandler.Callback) {
        val id: String = toDoItem.uniqueId!!
        val text: String = toDoItem.itemText!!
        val checked: Boolean = toDoItem.done!!

        val editor = myPreferences.edit()

        try {
            editor.putString(id, "$text,$checked")
            editor.apply()
            cb.onSuccess(toDoItem)
        } catch (ex: Exception) {
            cb.onError(ex.message!!)
        }

    }
}