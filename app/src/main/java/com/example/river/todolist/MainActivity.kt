package com.example.river.todolist

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //SharedPreferences
    lateinit var toDoItemPreferences: ToDoItemPreferences
    //資料來源
    lateinit var toDoItemList: MutableList<ToDoItem>
    //RecyclerView Adapter
    lateinit var toDoAdapter: ToDoItemAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //新增項目事件
        fab_01.setOnClickListener {
            addNewItemDialog()
        }

        //給值
        toDoItemPreferences = ToDoItemPreferences(this)
        toDoItemList = toDoItemPreferences.Str2ToDoItem()
        toDoAdapter = ToDoItemAdapter(this, toDoItemList, clickListener = {
            toDoItemPreferences.removeData(it)
            toDoAdapter.refreshView(toDoItemPreferences.Str2ToDoItem())
        })

        rv_01.layoutManager = LinearLayoutManager(this)
        rv_01.adapter = toDoAdapter
    }

    private fun addNewItemDialog() {
        val alert = AlertDialog.Builder(this)
        val itemEditText = EditText(this)
        alert
                .setMessage("Add New Item")
                .setTitle("Enter To Do Item Text")
                .setView(itemEditText)
                .setPositiveButton("Submit") { dialog, positiveButton ->
                    toDoItemPreferences.setData(null, itemEditText.text.toString())
                    toDoAdapter.refreshView(toDoItemPreferences.Str2ToDoItem())
                }
        alert.show()
    }
}