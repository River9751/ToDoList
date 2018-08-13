package com.example.river.todolist

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import javax.security.auth.callback.Callback


class MainActivity : AppCompatActivity() {

    //SharedPreferences
    //lateinit var toDoItemPreferences: ToDoItemPreferences
    //DataSource
    var toDoItemList: MutableList<ToDoItem> = arrayListOf()
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
        //dataHandler = ToDoItemPreferences(this)
        dataHandler = FirebaseHelper()
        //dataHandler = SQLiteDBManager(this)

        //sqLiteDBManager = SQLiteDBManager(this)
        //toDoItemPreferences = ToDoItemPreferences(this)
        //toDoItemList = toDoItemPreferences.getDatas()
        //toDoItemList = Helper.getDataList(this, Helper.dataSource.SharedPreferences)
        //toDoItemList = dataHandler.getAll()


        toDoAdapter = ToDoItemAdapter(this, toDoItemList, ::itemClicked) //把按下後的判斷方法當作參數傳入


        dataHandler.load(object : Callback, IDataHandler.Callback {
            override fun onSuccess(obj: Any?) {
                toDoAdapter.getAllItems(obj as MutableList<ToDoItem>)
            }

            override fun onError(errorMsg: String) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

        rv_01.layoutManager = LinearLayoutManager(this)
        rv_01.adapter = toDoAdapter
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
            else -> Toast.makeText(this, "Else Clicked", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 新增和修改 Dialog，uniqueId = "" 代表新增項目
     */
    private fun itemDialog(toDoItem: ToDoItem) {
        val alert = AlertDialog.Builder(this)
        val itemEditText = EditText(this)

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
                println("***AddNewItemToRecyclerView***")
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