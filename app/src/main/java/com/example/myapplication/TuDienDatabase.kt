package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TuDienDatabase(context: Context) : SQLiteOpenHelper(context, DATABASE, null, VERSION) {
    companion object{
        const val DATABASE = "SQLz1.db"
        const val VERSION = 1
        const val TABLE = "tudien123"
        const val ID = "id"
        const val ENGLISH = "english"
        const val VIETNAMESE = "vietnamese"
        const val CREATE_TABLE =
            " CREATE TABLE " + TABLE + "(" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    ENGLISH + " TEXT, " +
                    VIETNAMESE + " TEXT " +")"

    }
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if(oldVersion<2){
            db?.execSQL("DROP TABLE IF EXISTS " + TABLE)
            onCreate(db)
        }
    }
    fun getAllData(): MutableList<TuDien>{
        val list = mutableListOf<TuDien>()
        val db = readableDatabase
        val cursor = db.query(TABLE, null,null,null,null,null,null)
        if (cursor.moveToFirst()){
            do {
                val tuDien = TuDien()
                tuDien.id = cursor.getInt(0)
                tuDien.english = cursor.getString(1)
                tuDien.vietnamese = cursor.getString(2)
                list.add(tuDien)
            }while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }
    fun insertData(tudien: TuDien){
        val db = writableDatabase
        val values = ContentValues()
        values.put(ENGLISH, tudien.english)
        values.put(VIETNAMESE, tudien.vietnamese)
        db.insert(TABLE, null, values)
    }
    fun searchTuDien(strAnh: String): MutableList<TuDien>{
        val list = mutableListOf<TuDien>()
        val db = readableDatabase
        val cursor = db.query(TABLE, null, ENGLISH + " = ? ", arrayOf(strAnh),null,null,null)
        if (cursor.moveToFirst()){
            do {
                val tuDien = TuDien()
                tuDien.id = cursor.getInt(0)
                tuDien.english = cursor.getString(1)
                tuDien.vietnamese = cursor.getString(2)
                list.add(tuDien)
            }while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }
}