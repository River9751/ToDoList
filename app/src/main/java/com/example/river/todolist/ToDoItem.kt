package com.example.river.todolist

//class ToDoItem {
//    companion object Factory {
//        fun create(): ToDoItem = ToDoItem()
//    }
//    var objectId: String? = null
//    var itemText: String? = null
//    var done: Boolean? = false
//}

class ToDoItem {
    var uniqueId: String? = null
    var itemText: String? = null
    var done: Boolean? = null

    constructor(){}
    constructor(uniqueId: String,
                itemText: String,
                 done: Boolean){
        this.uniqueId=uniqueId
        this.itemText=itemText
        this.done=done
    }
}