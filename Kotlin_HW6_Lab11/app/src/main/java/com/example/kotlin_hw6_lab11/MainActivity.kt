package com.example.kotlin_hw6_lab11

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var adapter: ArrayAdapter<String>?=null
    private var items = ArrayList<String>()
    lateinit var dbrw:SQLiteDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        listView.adapter=adapter
        dbrw=MyDBHelper(this).writableDatabase
        btn_query.setOnClickListener {
            val c:Cursor
            if (ed_book.length()<1)
                c = dbrw.rawQuery("SELECT * FROM myTable",null)
            else
                c  =dbrw.rawQuery("SELECT * FROM myTable WHERE book LIKE '"+ed_book.text.toString()+"'",null)
            c.moveToFirst()
            items.clear()
            Toast.makeText(this, "共有" + c.count + "筆資料", Toast.LENGTH_SHORT).show()
            for (i in 0 until  c.count) {
                items.add("書名:" + c.getString(0) + "\t\t\t價格:" + c.getString(1))
                c.moveToNext()
            }
            adapter!!.notifyDataSetChanged()
            c.close()
        }
        btn_insert.setOnClickListener {
            if (ed_book.length() < 1 || ed_price.length() < 1)
                Toast.makeText(this, "欄位請勿留空", Toast.LENGTH_SHORT).show()
            else
            {
                try {
                    dbrw.execSQL("INSERT INTO myTable(book,price)VALUES(?,?)", arrayOf<Any>(ed_book.text.toString(), ed_price.text.toString()))
                    ed_book.text=null
                    ed_price.text=null
                }catch (e:Exception) {
                    Toast.makeText(this, "新增失敗$e", Toast.LENGTH_LONG).show()
                }
            }
        }

        btn_update.setOnClickListener {
            if (ed_book.length() < 1 || ed_price.length() < 1)
                Toast.makeText(this, "欄位請勿留空", Toast.LENGTH_SHORT).show()
            else
            {
                try {
                    dbrw.execSQL("UPDATE myTable SET price = " + ed_price.text.toString() + " WHERE book LIKE '" + ed_book.text.toString() + "'")
                    Toast.makeText(this, "更新書名" + ed_book.text.toString() + "    價格" + ed_price.text.toString(), Toast.LENGTH_SHORT).show()
                    ed_price.setText("")
                    ed_book.setText("")
                }catch (e:Exception) {
                    Toast.makeText(this, "更新失敗$e", Toast.LENGTH_LONG).show()
                }
            }
        }
        btn_delete.setOnClickListener {
            if (ed_book.length() < 1)
                    Toast.makeText(this, "書名請勿留空", Toast.LENGTH_SHORT).show()
            else{
                try {
                    dbrw.execSQL("DELETE FROM myTable WHERE book LIKE '" + ed_book.text.toString() + "'")
                    Toast.makeText(this, "刪除書名" + ed_book.text.toString(), Toast.LENGTH_SHORT).show()
                    ed_price.setText("")
                    ed_book.setText("")
                }catch (e:Exception){
                    Toast.makeText(this, "刪除失敗$e", Toast.LENGTH_LONG).show()
                }
            }
        }
        fun onDestroy()
        {
            super.onDestroy()
            dbrw.close()
        }
    }
}
