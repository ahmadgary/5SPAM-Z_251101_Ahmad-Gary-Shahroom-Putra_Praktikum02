package com.dipa.praktikum02

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE " + TABLE_MAHASISWA + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nim TEXT NOT NULL, " +
                "nama TEXT NOT NULL, " +
                "alamat TEXT, " +
                "jurusan TEXT, " +
                "nomor_hp TEXT)"
        db.execSQL(createTable)
        seedData(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAHASISWA)
        onCreate(db)
    }

    private fun seedData(db: SQLiteDatabase) {
        addMahasiswa(db, "21001", "Ahmad Fauzi", "Jl. Pettarani No. 10", "Sistem Informasi", "081234567890")
        addMahasiswa(db, "21002", "Siti Nurhaliza", "Jl. Perintis Kemerdekaan", "Sistem Informasi", "082198765432")
        addMahasiswa(db, "21003", "Budi Santoso", "Jl. Rappocini No. 12", "Sistem Informasi", "081345678901")
    }

    private fun addMahasiswa(
        db: SQLiteDatabase,
        nim: String,
        nama: String,
        alamat: String,
        jurusan: String,
        nomorHp: String
    ) {
        val values = ContentValues().apply {
            put("nim", nim)
            put("nama", nama)
            put("alamat", alamat)
            put("jurusan", jurusan)
            put("nomor_hp", nomorHp)
        }
        db.insert(TABLE_MAHASISWA, null, values)
    }

    fun getAllMahasiswa(): List<Mahasiswa> {
        val list = mutableListOf<Mahasiswa>()
        val cursor = readableDatabase.query(
            TABLE_MAHASISWA, null, null, null, null, null, "nama ASC"
        )
        cursor.use {
            while (it.moveToNext()) {
                list.add(
                    Mahasiswa(
                        id = it.getInt(it.getColumnIndexOrThrow("id")),
                        nim = it.getString(it.getColumnIndexOrThrow("nim")),
                        nama = it.getString(it.getColumnIndexOrThrow("nama")),
                        alamat = it.getString(it.getColumnIndexOrThrow("alamat")),
                        jurusan = it.getString(it.getColumnIndexOrThrow("jurusan")),
                        nomorHp = it.getString(it.getColumnIndexOrThrow("nomor_hp"))
                    )
                )
            }
        }
        return list
    }

    companion object {
        private const val DB_NAME = "praktikum.db"
        private const val DB_VERSION = 1
        const val TABLE_MAHASISWA = "mahasiswa"
    }
}
