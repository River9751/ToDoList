package com.example.river.todolist.helper

import com.example.river.todolist.IDataHandler
import com.example.river.todolist.ToDoItem
import com.google.firebase.database.*
import com.google.firebase.database.DatabaseReference
import java.util.*


class FirebaseHelper : IDataHandler {

    private var fireDB = FirebaseDatabase.getInstance()
    private var myRef: DatabaseReference

    var list: MutableList<ToDoItem>

    init {
        myRef = fireDB.getReference("ToDoList")
        list = ArrayList()
    }

    override fun load(cb: IDataHandler.Callback) {

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    try {
                        list.clear()
                        for (item in p0.children) {
                            var toDoItem = item.getValue(ToDoItem::class.java)
                            list.add(toDoItem!!)
                        }
                        cb.onSuccess(list)
                    } catch (ex: Exception) {
                        cb.onError(ex.message!!)
                    }
                }
            }
        })

        myRef.addChildEventListener(object : ChildEventListener {

            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                var item = p0.getValue(ToDoItem::class.java)
                callback?.onSuccess(item).also { callback = null }
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                //資料庫有資料新增後觸發
                var item = p0.getValue(ToDoItem::class.java)
                callback?.onSuccess(item).also { callback = null }
            }

            override fun onChildRemoved(p0: DataSnapshot) {
                var item = p0.getValue(ToDoItem::class.java)
                callback?.onSuccess(item?.uniqueId).also { callback = null }
            }
        })
    }

    var callback: IDataHandler.Callback? = null

    override fun insert(itemText: String, cb: IDataHandler.Callback) {
        this.callback = cb

        try {
            val uniqueId: String = UUID.randomUUID().toString()
            val toDoItem = ToDoItem(uniqueId, itemText, false)
            myRef.child(toDoItem.uniqueId!!).setValue(toDoItem)
        } catch (ex: Exception) {
            cb.onError(ex.message!!)
        }
    }

    override fun delete(uniqueId: String, cb: IDataHandler.Callback) {

        /*
        從 Client 端按下刪除按鈕才會觸發該方法
        如果直接從 Firebase 網頁端刪除，便不會觸發
        */

        this.callback = cb

        try {
            myRef.child(uniqueId).removeValue()
        } catch (ex: Exception) {
            cb.onError(ex.message!!)
        }
    }

    override fun update(toDoItem: ToDoItem, cb: IDataHandler.Callback) {
        this.callback = cb

        try {
            myRef.child(toDoItem.uniqueId!!).setValue(toDoItem)
        } catch (ex: Exception) {
            cb.onError(ex.message!!)
        }
    }
}