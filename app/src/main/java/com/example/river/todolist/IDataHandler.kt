package com.example.river.todolist

interface IDataHandler {

    fun insert(itemText: String): String?

    fun update(toDoItem: ToDoItem): Boolean

    fun delete(uniqueId: String): Boolean

    fun getAll(): MutableList<ToDoItem>
}