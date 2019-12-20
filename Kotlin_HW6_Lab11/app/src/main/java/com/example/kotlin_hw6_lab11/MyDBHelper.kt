package com.example.kotlin_hw6_lab11

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDBHelper(context: Context) :SQLiteOpenHelper(context, name,null, version){
    companion object{
        private val name = "mdatabase.db"
        private val version = 1
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS myTable")
        onCreate(db)
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE myTable(book text PRIMARY KEY,price integer NOT NULL)")
    }
}