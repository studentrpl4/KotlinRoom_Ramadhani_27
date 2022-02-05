package com.example.kotlinroom_ramadhani_27

import android.content.Intent
import android.graphics.Movie
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinroom_ramadhani_27.room.Constant
import com.example.kotlinroom_ramadhani_27.room.Manga
import com.example.kotlinroom_ramadhani_27.room.MangaDb
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    val db by lazy { MangaDb(this) }
    lateinit var mangaAdapter: MangaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupListener()
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        loadManga()
    }

    fun loadManga(){
        CoroutineScope(Dispatchers.IO).launch {
            val manga = db.mangaDao().getMangas()
            Log.d("MainActivity",  "dbresponse $manga")
            withContext(Dispatchers.Main){
                mangaAdapter.setData(manga)
            }
        }
    }

    fun setupListener(){
        add_manga.setOnClickListener {
            intentEdit(0, Constant.TYPE_CREATE)
        }
    }

    fun intentEdit(mangaId: Int, intentType: Int){
        startActivity(
            Intent(applicationContext, AddActivity::class.java)
                .putExtra("intent_id", mangaId)
                .putExtra("intent_type", intentType)
        )
    }

    private fun setupRecyclerView() {
        mangaAdapter = MangaAdapter(arrayListOf(), object : MangaAdapter.OnAdapterListener {
            override fun onClick(manga: Manga) {
                // read detail manga
                intentEdit(manga.id, Constant.TYPE_READ)
            }

            override fun onUpdate(manga: Manga) {
                intentEdit(manga.id, Constant.TYPE_UPDATE)
            }

            override fun onDelete(manga: Manga) {
                deleteDialog(manga)
            }
        })
        rv_manga.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = mangaAdapter
        }
    }

    private fun deleteDialog(manga: Manga) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Konfirmasi")
            setMessage("Apakah anda ingin menghapus Komik -${manga.title}-?")
            setNegativeButton("Cancel") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Delete") { dialogInterface, i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.mangaDao().deleteManga(manga)
                    loadManga()
                }
            }
        }
        alertDialog.show()
    }
}