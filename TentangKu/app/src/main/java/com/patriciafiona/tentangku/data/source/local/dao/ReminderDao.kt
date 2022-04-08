package com.patriciafiona.tentangku.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.patriciafiona.tentangku.data.source.local.entity.Note
import com.patriciafiona.tentangku.data.source.local.entity.Reminder

@Dao
interface ReminderDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(reminder: Reminder)
    @Update
    fun update(reminder: Reminder)
    @Delete
    fun delete(reminder: Reminder)
    @Query("SELECT * from reminder ORDER BY id ASC")
    fun getAllReminder(): LiveData<List<Reminder>>
}