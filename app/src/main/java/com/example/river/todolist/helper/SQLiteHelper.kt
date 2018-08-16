package com.example.river.todolist.helper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.river.todolist.IDataHandler
import com.example.river.todolist.ToDoItem
import java.util.*

class SQLiteHelper(context: Context) : IDataHandler {
    private var db: SQLiteDatabase
    private var version = 1

    //表示該區塊內都是 static
    companion object {
        private const val DB_NAME = "ToDoList.db"
        private const val TABLE_NAME = "ToDoItemList"

        // 表格欄位名稱
        private const val UNIQUE_ID = "uniqueId"
        private const val ITEM_TEXT = "itemText"
        private const val DONE = "done"

        private const val CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " (" +
                UNIQUE_ID + " TEXT NOT NULL, " +
                ITEM_TEXT + " TEXT NOT NULL, " +
                DONE + " INTEGER NOT NULL " + ")"
    }

    init {
        val dbHelper = MyDBHelper(context, DB_NAME, TABLE_NAME, version, CREATE_TABLE_SQL)
        db = dbHelper.writableDatabase
    }

    override fun load(cb: IDataHandler.Callback) {
        val cursor = queryAll()
        val list = ArrayList<ToDoItem>()
        if (cursor.moveToFirst()) {
            do {
                val uniqueId = cursor.getString(cursor.getColumnIndex("uniqueId"))
                val itemText = cursor.getString(cursor.getColumnIndex("itemText"))
                val done: Boolean = cursor.getInt(cursor.getColumnIndex("done")) == 1

                list.add(ToDoItem(uniqueId, itemText, done))
            } while (cursor.moveToNext())
        }
        cb.onSuccess(list)
    }

    override fun insert(itemText: String, cb: IDataHandler.Callback) {
        val uniqueId: String = UUID.randomUUID().toString()
        val values = ContentValues()
        values.put("uniqueId", uniqueId)
        values.put("itemText", itemText)
        values.put("done", 0)

        try {
            db.insert(TABLE_NAME, "", values)
            cb.onSuccess(ToDoItem(uniqueId, itemText, false))
        } catch (ex: Exception) {
            cb.onError(ex.message!!)
        }
    }

    override fun delete(uniqueId: String, cb: IDataHandler.Callback) {
        try {
            db.delete(TABLE_NAME, "uniqueId = '$uniqueId'", null)
            cb.onSuccess(uniqueId)
        } catch (ex: Exception) {
            cb.onError(ex.message!!)
        }
    }

    override fun update(toDoItem: ToDoItem, cb: IDataHandler.Callback) {
        val uniqueId: String = toDoItem.uniqueId!!
        val itemText: String = toDoItem.itemText!!
        val done: Int = if (toDoItem.done!!) 1 else 0
        val values = ContentValues()
        values.put("uniqueId", uniqueId)
        values.put("itemText", itemText)
        values.put("done", done)
        try {
            db.update(TABLE_NAME, values, "uniqueId = '$uniqueId'", null)
            cb.onSuccess(toDoItem)
        } catch (ex: Exception) {
            println(ex.message)
        }
    }

    private fun queryAll(): Cursor {
        return db.rawQuery("select * from $TABLE_NAME", null)
    }
}


////////////////////////////////////////////////////////////////////////////////////////////


class MyDBHelper
    : SQLiteOpenHelper {

    private var ctx: Context
    private var dbName: String
    private var tableName: String
    private var version: Int
    private var createSQL: String

    constructor(ctx: Context, dbName: String, tableName: String, version: Int, createSQL: String)
            : super(ctx, dbName, null, version) {
        this.ctx = ctx
        this.dbName = dbName
        this.tableName = tableName
        this.version = version
        this.createSQL = createSQL
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(createSQL)
        Toast.makeText(this.ctx, " database is created", Toast.LENGTH_LONG).show()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("Drop table IF EXISTS $tableName")
    }
}