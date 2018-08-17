package com.example.river.todolist.helper

import com.example.river.todolist.IDataHandler
import com.example.river.todolist.ToDoItem
import com.google.android.gms.common.util.CollectionUtils.mutableListOf
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class FirestoreHelper : IDataHandler {

    private var fireDB: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun load(cb: IDataHandler.Callback) {
        fireDB.collection("ToDoList")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val list: MutableList<ToDoItem> = mutableListOf()
                        for (document in task.result) {
                            list.add(document.toObject(ToDoItem::class.java))
                        }
                        cb.onSuccess(list)
                                .also { callback = null }
                    } else {
                        cb.onError(task.exception?.message!!)
                                .also { callback = null }
                    }
                }
    }

    var callback: IDataHandler.Callback? = null

    override fun insert(itemText: String, cb: IDataHandler.Callback) {
        this.callback = cb

        val uniqueId: String = UUID.randomUUID().toString()
        var data: HashMap<String, Any> = hashMapOf()
        //data.put("uniqueId", uniqueId)
        data["uniqueId"] = uniqueId
        data["itemText"] = itemText
        data["done"] = false

        fireDB.collection("ToDoList")
                .document(uniqueId)
                .set(data)
                .addOnSuccessListener {
                    cb.onSuccess(ToDoItem(uniqueId, itemText, false)
                            .also { callback = null })
                }
                .addOnFailureListener { e ->
                    cb.onError(e.message!!)
                            .also { callback = null }
                }
    }

    override fun delete(uniqueId: String, cb: IDataHandler.Callback) {
        this.callback = cb

        fireDB.collection("ToDoList").document(uniqueId)
                .delete()
                .addOnSuccessListener {
                    cb.onSuccess(uniqueId)
                            .also { callback = null }
                }
                .addOnFailureListener { e ->
                    cb.onError(e.message!!)
                            .also { callback = null }
                }
    }

    override fun update(toDoItem: ToDoItem, cb: IDataHandler.Callback) {
        this.callback = cb

        fireDB.collection("ToDoList")
                .document(toDoItem.uniqueId!!)
                .set(toDoItem)
                .addOnSuccessListener {
                    cb.onSuccess(toDoItem)
                            .also { callback = null }
                }.addOnFailureListener { e ->
                    cb.onError(e.message!!)
                            .also { callback = null }
                }
    }
}