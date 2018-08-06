package com.example.river.todolist

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class MyDBHelper
    : SQLiteOpenHelper {

    //在建構子給值
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