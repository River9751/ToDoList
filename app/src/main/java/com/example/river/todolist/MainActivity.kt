package com.example.river.todolist

import android.content.ContentValues
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //SharedPreferences
    //lateinit var toDoItemPreferences: ToDoItemPreferences
    //DataSource
    lateinit var toDoItemList: MutableList<ToDoItem>
    //RecyclerView Adapter
    lateinit var toDoAdapter: ToDoItemAdapter
    //SQLite
    lateinit var sqLiteDBManager: SQLiteDBManager


    lateinit var dataHandler: IDataHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //新增項目事件
        fab_01.setOnClickListener {
            itemDialog(toDoItem = ToDoItem("", "", false))
        }

        //給值
        dataHandler = ToDoItemPreferences(this)
        //dataHandler = SQLiteDBManager(this)
        //sqLiteDBManager = SQLiteDBManager(this)
        //toDoItemPreferences = ToDoItemPreferences(this)
        //toDoItemList = toDoItemPreferences.getDatas()
        //toDoItemList = Helper.getDataList(this, Helper.dataSource.SharedPreferences)
        toDoItemList = dataHandler.getAll()
        toDoAdapter = ToDoItemAdapter(
                this,
                toDoItemList,
                ::ItemClicked) //把按下後的判斷方法當作參數傳入

        rv_01.layoutManager = LinearLayoutManager(this)
        rv_01.adapter = toDoAdapter
    }

    //RecyclerView中按下Item要做的事情
    fun ItemClicked(id: Int, toDoItem: ToDoItem): Unit {
        when (id) {
            //刪除
            R.id.iv_cross -> {
                if (dataHandler.delete(toDoItem.uniqueId)) {
                    toDoAdapter.deleteItem(toDoItem)
                }
            }
            //修改
            R.id.tv_item_text -> {
                itemDialog(toDoItem)
            }
            //勾選
            R.id.cb_item_is_done -> {

                if (dataHandler.update(toDoItem)) {
                    toDoAdapter.updateItem(toDoItem)
                }
            }
            else -> {
                Toast.makeText(this, "Else Clicked", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //新增或修改項目Dialog
    private fun itemDialog(toDoItem: ToDoItem): String {
        //uniqueId ="" 代表要新增
        val alert = AlertDialog.Builder(this)
        val itemEditText = EditText(this)

        var strMessage = "Add New Item"

        if (toDoItem.uniqueId != "") {
            //修改
            strMessage = "Modify Item"
            itemEditText.setText(toDoItem.itemText)
            alert.setPositiveButton("Update") { dialog, positiveButton ->
                toDoItem.itemText = itemEditText.text.toString()
                if (dataHandler.update(toDoItem)) {
                    toDoAdapter.updateItem(toDoItem)
                }
            }
        } else {
            //新增
            alert.setPositiveButton("Add") { dialog, positiveButton ->
                //dataHandler.insert(itemEditText.text.toString())
                //AddNewData(itemEditText.text.toString())
                val uniqueId = dataHandler.insert(itemEditText.text.toString())
                if (uniqueId != null) {
                    toDoAdapter.addNewItem(
                            toDoItem = ToDoItem(uniqueId, itemEditText.text.toString(), false))
                }
            }
        }
        alert
                .setMessage(strMessage)
                .setTitle("Enter To Do Item Text")
                .setView(itemEditText)
                .show()

        return itemEditText.text.toString()
    }


    fun AddNewData(itemEditText: String) {
        //新增資料到 SQLite 資料庫
        var uniqueId = sqLiteDBManager.insert(itemEditText)

        //新增到 Adapter 的資料List
        if (uniqueId != null) {
            //val uniqueId = toDoItemPreferences.addData(itemEditText)
            toDoAdapter.addNewItem(
                    toDoItem = ToDoItem(
                            uniqueId,
                            itemEditText,
                            false))
        }
    }
}