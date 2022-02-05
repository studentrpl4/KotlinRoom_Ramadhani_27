package com.example.kotlinroom_ramadhani_27.room

import androidx.room.*

@Dao
interface MangaDao {

    @Insert
    suspend fun addManga(manga: Manga)

    @Update
    suspend fun updateManga(manga: Manga)

    @Delete
    suspend fun deleteManga(manga: Manga)

    @Query("SELECT * FROM manga")
    suspend fun getMangas():List<Manga>

    @Query("SELECT * FROM manga WHERE id=:manga_id")
    suspend fun getManga(manga_id: Int):List<Manga>
}