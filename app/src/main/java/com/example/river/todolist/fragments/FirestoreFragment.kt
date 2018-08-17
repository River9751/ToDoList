package com.example.river.todolist.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.example.river.todolist.*
import com.example.river.todolist.helper.FirebaseHelper
import com.example.river.todolist.helper.FirestoreHelper
import kotlinx.android.synthetic.main.fragment_firebase.*
import kotlinx.android.synthetic.main.fragment_firestore.*
import javax.security.auth.callback.Callback

class FirestoreFragment : Fragment() {

    lateinit var ctx: Context

    lateinit var toDoAdapter: ToDoItemAdapter

    var toDoItemList: MutableList<ToDoItem> = arrayListOf<ToDoItem>()

    lateinit var dataHandler: IDataHandler


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_firestore, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        ctx = this.context!!
        /**
         * context 為所依附的 Activity
         */
        //println("*** ${this.context.toString()} ***")

        fab_firestore.setOnClickListener {
            itemDialog(toDoItem = ToDoItem("", "", false))
        }

        toDoAdapter = ToDoItemAdapter(
                ctx,
                toDoItemList,
                ::itemClicked) //把按下後的判斷方法當作參數傳入
        dataHandler = FirestoreHelper()

        dataHandler.load(object : Callback, IDataHandler.Callback {
            override fun onSuccess(obj: Any?) {
                toDoAdapter.getAllItems(obj as MutableList<ToDoItem>)
            }

            override fun onError(errorMsg: String) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

        rv_firestore.adapter = toDoAdapter
        rv_firestore.layoutManager = LinearLayoutManager(super.getActivity())

        super.onActivityCreated(savedInstanceState)
    }


    /**
     * 按下介面控制項的事件
     */
    private fun itemClicked(id: Int, toDoItem: ToDoItem): Unit {
        when (id) {
            //刪除
            R.id.iv_cross -> deleteData(toDoItem.uniqueId!!)
            //修改
            R.id.tv_item_text -> itemDialog(toDoItem)
            //勾選
            R.id.cb_item_is_done -> updateData(toDoItem)
            //
            else -> Toast.makeText(ctx, "Else Clicked", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 新增和修改 Dialog，uniqueId = "" 代表新增項目
     */
    private fun itemDialog(toDoItem: ToDoItem) {
        val alert = AlertDialog.Builder(ctx)
        val itemEditText = EditText(ctx)

        var strMessage = "Add New Item"

        if (toDoItem.uniqueId != "") {
            //修改
            strMessage = "Modify Item"
            itemEditText.setText(toDoItem.itemText)
            alert.setPositiveButton("Update") { dialog, positiveButton ->
                toDoItem.itemText = itemEditText.text.toString()
                updateData(toDoItem)
            }
        } else {
            //新增
            alert.setPositiveButton("Add") { dialog, positiveButton ->
                insertData(itemEditText.text.toString())
            }
        }
        alert
                .setMessage(strMessage)
                .setTitle("Enter To Do Item Text")
                .setView(itemEditText)
                .show()
    }

    private fun insertData(itemText: String) {
        dataHandler.insert(itemText, object : Callback, IDataHandler.Callback {
            override fun onSuccess(obj: Any?) {
                toDoAdapter.addNewItem(obj as ToDoItem)
            }

            override fun onError(errorMsg: String) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    private fun updateData(toDoItem: ToDoItem) {
        dataHandler.update(toDoItem, object : Callback, IDataHandler.Callback {
            override fun onSuccess(obj: Any?) {
                var item = obj as ToDoItem

                toDoAdapter.updateItem(obj as ToDoItem)
            }

            override fun onError(errorMsg: String) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    private fun deleteData(uniqueId: String) {
        dataHandler.delete(uniqueId, object : Callback, IDataHandler.Callback {
            override fun onSuccess(obj: Any?) {
                toDoAdapter.deleteItem(obj as String)
            }

            override fun onError(errorMsg: String) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

}
