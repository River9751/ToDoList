package com.example.river.todolist

interface IDataHandler {

    fun load(cb: Callback)
    fun insert(itemText: String, cb: Callback)
    fun delete(uniqueId: String, cb: Callback)
    fun update(toDoItem: ToDoItem, cb: Callback)

    interface Callback {
        fun onSuccess(obj: Any?)
        fun onError(errorMsg: String)
    }
}