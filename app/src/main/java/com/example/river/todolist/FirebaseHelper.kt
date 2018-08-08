package com.example.river.todolist

import com.google.firebase.database.*
import com.google.firebase.database.DatabaseReference


class FirebaseHelper : IDataHandler {

    private var fireDB = FirebaseDatabase.getInstance()
    private var myRef: DatabaseReference
    var list: MutableList<ToDoItem>

    init {
        myRef = fireDB.getReference("ToDoList")
        list = ArrayList()

        myRef.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                //var aa:ToDoItem = p0.value.
//                insert("")
                //var bb = 0
            }

            override fun onChildRemoved(p0: DataSnapshot) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    try {
                        for (item in dataSnapshot.children) {
                            var toDoItem = item.getValue(ToDoItem::class.java)
                            list.add(toDoItem!!)
                        }
                        getAllListener.invoke(list)

                    } catch (ex: Exception) {
                        println(ex.toString())
                    }
                }
            }
        })
    }

    override fun initListener(
            all: (list: MutableList<ToDoItem>) -> Unit,
            insert: (uniqueId: String, itemText: String) -> Unit,
            update: (toDoItem: ToDoItem) -> Unit,
            delete: (toDoItem: ToDoItem) -> Unit
    ) {
        getAllListener = all
        insertListener = insert
        updateListener = update
        deleteListener = delete
    }

    override lateinit var deleteListener: (toDoItem: ToDoItem) -> Unit

    override lateinit var updateListener: (toDoItem: ToDoItem) -> Unit

    override lateinit var insertListener: (uniqueId: String, itemText: String) -> Unit

    override lateinit var getAllListener: (list: MutableList<ToDoItem>) -> Unit

    override fun insert(itemText: String) {
        //myRef.setValue()

        myRef.setValue("1234")
    }

    override fun update(toDoItem: ToDoItem) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(toDoItem: ToDoItem) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAll() {
        //Nothing to do here.
    }
}







