package com.example.river.todolist

interface IDataHandler {

//    fun initData(updateViewFunc: (list: MutableList<ToDoItem>) -> Unit)

    fun insert(itemText: String)

    fun update(toDoItem: ToDoItem)

    fun delete(toDoItem: ToDoItem)

    //fun getAll(): MutableList<ToDoItem>
    fun getAll()

    /////////////////////////////////////////////////////////////////////

    var getAllListener: (list: MutableList<ToDoItem>) -> Unit

    var insertListener: (uniqueId: String, itemText: String) -> Unit

    var updateListener: (toDoItem:ToDoItem) -> Unit

    var deleteListener: (toDoItem: ToDoItem) -> Unit

    fun initListener(
            all: (list: MutableList<ToDoItem>) -> Unit,
            insert: (uniqueId: String, itemText: String) -> Unit,
            update: (toDoItem:ToDoItem) -> Unit,
            delete: (toDoItem:ToDoItem) -> Unit
    )

}