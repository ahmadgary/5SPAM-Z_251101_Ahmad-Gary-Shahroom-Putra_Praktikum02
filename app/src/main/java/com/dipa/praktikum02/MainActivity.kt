package com.dipa.praktikum02

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var db: DatabaseHelper
    private lateinit var adapter: MahasiswaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etNim = findViewById<EditText>(R.id.etNim)
        val etNama = findViewById<EditText>(R.id.etNama)
        val etAlamat = findViewById<EditText>(R.id.etAlamat)
        val etJurusan = findViewById<EditText>(R.id.etJurusan)
        val etNomorHp = findViewById<EditText>(R.id.etNomorHp)
        val btnSimpan = findViewById<Button>(R.id.btnSimpan)
        val listView = findViewById<ListView>(R.id.listMahasiswa)

        db = DatabaseHelper(this)
        // Baca seluruh data dari database, lalu tampilkan pada ListView.
        val dataMahasiswa = db.getAllMahasiswa().toMutableList()
        adapter = MahasiswaAdapter(this, dataMahasiswa)
        listView.adapter = adapter

        btnSimpan.setOnClickListener {
            val nim = etNim.text.toString().trim()
            val nama = etNama.text.toString().trim()
            val alamat = etAlamat.text.toString().trim()
            val jurusan = etJurusan.text.toString().trim()
            val nomorHp = etNomorHp.text.toString().trim()

            if (nim.isEmpty() || nama.isEmpty()) {
                Toast.makeText(this, "NIM dan Nama wajib diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Sementara hanya menambah di memori; penyimpanan permanen (Create)
            // dibahas pada Modul 3.
            dataMahasiswa.add(
                Mahasiswa(
                    nim = nim, nama = nama, alamat = alamat,
                    jurusan = jurusan, nomorHp = nomorHp
                )
            )

            adapter.notifyDataSetChanged()
            Toast.makeText(this, "Data mahasiswa ditambahkan", Toast.LENGTH_SHORT).show()

            etNim.text.clear()
            etNama.text.clear()
            etAlamat.text.clear()
            etJurusan.text.clear()
            etNomorHp.text.clear()
        }
    }
}
