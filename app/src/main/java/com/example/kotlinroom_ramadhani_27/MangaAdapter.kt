package com.example.kotlinroom_ramadhani_27

import android.graphics.Movie
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinroom_ramadhani_27.room.Manga
import kotlinx.android.synthetic.main.list_manga.view.*

class MangaAdapter(private val mangas: ArrayList<Manga> ,private val listener : OnAdapterListener) : RecyclerView.Adapter<MangaAdapter.MangaViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaViewHolder {
        return MangaViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_manga, parent, false)
        )
    }

    override fun getItemCount() = mangas.size

    override fun onBindViewHolder(holder: MangaViewHolder, position: Int) {
        val manga = mangas[position]
        holder.view.text_title.text = manga.title
        holder.view.text_title.setOnClickListener {
            listener.onClick(manga)
        }
        holder.view.icon_edit.setOnClickListener {
            listener.onUpdate(manga)
        }
        holder.view.icon_delete.setOnClickListener {
            listener.onDelete(manga)
        }
    }

    class MangaViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(list: List<Manga>){
        mangas.clear()
        mangas.addAll(list)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(manga: Manga)
        fun onUpdate(manga: Manga)
        fun onDelete(manga: Manga)
    }
}