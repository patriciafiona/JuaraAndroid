package com.patriciafiona.tentangku.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.patriciafiona.tentangku.data.source.local.entity.Note

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note: Note)
    @Update
    fun update(note: Note)
    @Delete
    fun delete(note: Note)
    @Query("SELECT * from note ORDER BY id ASC")
    fun getAllNotes(): LiveData<List<Note>>
}