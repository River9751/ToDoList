package com.example.river.todolist

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import java.util.*

class SQLiteDBManager : IDataHandler {

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

    constructor(context: Context) {
        val dbHelper = MyDBHelper(context, DB_NAME, TABLE_NAME, version, CREATE_TABLE_SQL)
        db = dbHelper.writableDatabase
    }

    override fun insert(itemText: String): String? {
        val uniqueId: String = UUID.randomUUID().toString()
        val values = ContentValues()
        values.put("uniqueId", uniqueId)
        values.put("itemText", itemText)
        values.put("done", 0)

        return try {
            db.insert(TABLE_NAME, "", values)
            uniqueId
        } catch (e: Exception) {
            null
        }
    }

    override fun update(toDoItem: ToDoItem): Boolean {
        val uniqueId: String = toDoItem.uniqueId
        val itemText: String = toDoItem.itemText
        val done: Int = if (toDoItem.done) 1 else 0
        val values = ContentValues()
        values.put("uniqueId", uniqueId)
        values.put("itemText", itemText)
        values.put("done", done)
        return try {
            db.update(TABLE_NAME, values, "uniqueId = '$uniqueId'", null)
            true
        } catch (ex: Exception) {
            println(ex.message)
            false
        }
    }

    override fun delete(uniqueId: String): Boolean {
        return try {
            db.delete(TABLE_NAME, "uniqueId = '$uniqueId'", null)
            true
        } catch (ex: Exception) {
            false
        }
    }

    override fun getAll(): MutableList<ToDoItem> {
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
        return list
    }


    fun queryAll(): Cursor {
        return db.rawQuery("select * from $TABLE_NAME", null)
    }


    private fun dropTable() {
        db.execSQL("DROP TABLE ToDoItemList")
    }
}