package com.example.river.todolist

import android.content.Context
import android.content.SharedPreferences
import java.util.*

class ToDoItemPreferences : IDataHandler {
    override fun load(cb: IDataHandler.Callback) {
        try {
            var list: MutableList<ToDoItem> = mutableListOf()
            myPreferences.all.keys.forEach {
                val text: String = myPreferences.getString(it, "default")
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
            cb.onSuccess(null)
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
//    override lateinit var deleteListener: (toDoItem: ToDoItem) -> Unit
//
//    override lateinit var updateListener: (toDoItem: ToDoItem) -> Unit
//
//    override lateinit var getAllListener: (list: MutableList<ToDoItem>) -> Unit
//
//    override lateinit var insertListener: (uniqueId: String, itemText: String) -> Unit
//
//    override fun initListener(
//            all: (list: MutableList<ToDoItem>) -> Unit,
//            insert: (uniqueId: String, itemText: String) -> Unit,
//            update: (toDoItem: ToDoItem) -> Unit,
//            delete: (toDoItem: ToDoItem) -> Unit
//    ) {
//        getAllListener = all
//        insertListener = insert
//        updateListener = update
//        deleteListener = delete
//    }

    private val preferencesName: String = "ToDoItemPreferences"
    private val myPreferences: SharedPreferences
    private val context: Context

    constructor(context: Context) {
        this.context = context
        myPreferences = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
    }

//    override fun insert(itemText: String) {
//        val uniqueId: String = UUID.randomUUID().toString()
//        val editor = myPreferences.edit()
//
//        var result = try {
//            editor.putString(uniqueId, "$itemText, false")
//            editor.apply()
//            true
//        } catch (ex: Exception) {
//            false
//        }
//
//        if (result) insertListener.invoke(uniqueId, itemText)
//    }
//
//    override fun update(toDoItem: ToDoItem) {
//        val id: String = toDoItem.uniqueId!!
//        val text: String = toDoItem.itemText!!
//        val checked: Boolean = toDoItem.done!!
//
//        val editor = myPreferences.edit()
//
//        val result = try {
//            editor.putString(id, "$text,$checked")
//            editor.apply()
//            true
//        } catch (e: Exception) {
//            false
//        }
//
//        if (result) updateListener.invoke(toDoItem)
//    }
//
//    override fun delete(toDoItem: ToDoItem) {
//        val editor = myPreferences.edit()
//        var result = try {
//            editor.remove(toDoItem.uniqueId)
//            editor.apply()
//            true
//        } catch (e: Exception) {
//            false
//        }
//
//        if (result) deleteListener.invoke(toDoItem)
//    }
//
//    override fun getAll() {
//        var list: MutableList<ToDoItem> = mutableListOf()
//        myPreferences.all.keys.forEach {
//            val text: String = myPreferences.getString(it, "default")
//            val strList: List<String> = text.split(",".toRegex())
//            val itemText: String = strList[0]
//            val checked: Boolean = strList[1] == "true"
//            list.add(ToDoItem(it, itemText, checked))
//        }
//        //return list
//
//        getAllListener.invoke(list)
//    }
}