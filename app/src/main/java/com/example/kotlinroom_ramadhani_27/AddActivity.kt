package com.example.kotlinroom_ramadhani_27

import android.graphics.Movie
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.kotlinroom_ramadhani_27.room.Constant
import com.example.kotlinroom_ramadhani_27.room.Manga
import com.example.kotlinroom_ramadhani_27.room.MangaDb
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddActivity : AppCompatActivity() {

    val db by lazy {MangaDb(this)}
    private var mangaId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        setupView()
        setupListener()
    }

    fun setupView() {
        val intentType = intent.getIntExtra("intent_type", 0)
        when (intentType) {
            Constant.TYPE_CREATE -> {
                btn_update.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                btn_save.visibility = View.GONE
                btn_update.visibility = View.GONE
                getManga()
            }
            Constant.TYPE_UPDATE -> {
                btn_save.visibility = View.GONE
                getManga()
            }
        }
    }

    private fun setupListener() {
        btn_save.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.mangaDao().addManga(
                    Manga(
                        0,
                        et_title.text.toString(),
                        et_desc.text.toString()
                    )
                )
                finish()
            }
        }
        btn_update.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.mangaDao().updateManga(
                    Manga(
                        mangaId,
                        et_title.text.toString(),
                        et_desc.text.toString()
                    )
                )
                finish()
            }
        }
    }

    fun getManga() {
        mangaId = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val mangas = db.mangaDao().getManga( mangaId )[0]
            et_title.setText(mangas.title)
            et_desc.setText(mangas.desc)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}