package com.example.myapplication

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.databinding.ItemBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var db: TuDienDatabase
    lateinit var adapter: TuDienAdapter
    lateinit var rcv: RecyclerView
    var list = mutableListOf<TuDien>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = TuDienDatabase(this)
        adapter = TuDienAdapter()
        rcv = binding.rcv
        rcv.layoutManager = LinearLayoutManager(this)
        rcv.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))
        rcv.adapter = adapter

        binding.btnAdd.setOnClickListener {
            themDuLieu()
        }
        binding.btnSearch.setOnClickListener {
            val str = binding.edtEnglish.text.toString()
            timKiem(str)
        }
        getAllDuLieu()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun timKiem(strAnh: String) {
        var listSearch = mutableListOf<TuDien>()
        listSearch = db.searchTuDien(strAnh)
        adapter.setData(listSearch)
    }

    private fun getAllDuLieu() {
        list = db.getAllData()
        adapter.setData(list)
    }

    private fun themDuLieu() {
        val english = binding.edtEnglish.text.toString()
        val vietnamese = binding.edtVietnamese.text.toString()

        if(TextUtils.isEmpty(english)||TextUtils.isEmpty(vietnamese)){
            Toast.makeText(this,"KHONG DUOC DE TRONG !", Toast.LENGTH_SHORT).show()
            return
        }
        if(checkSo(english)||checkSo(vietnamese)){
            Toast.makeText(this,"HAY NHAP VAO 1 SO !", Toast.LENGTH_SHORT).show()
            return
        }
        val tuDien = TuDien(english = english, vietnamese = vietnamese)
        db.insertData(tuDien)
        binding.edtEnglish.setText("")
        binding.edtVietnamese.setText("")
        getAllDuLieu()
    }
    private fun checkSo(str: String): Boolean{
        return str.matches("\\d+".toRegex())
    }
}