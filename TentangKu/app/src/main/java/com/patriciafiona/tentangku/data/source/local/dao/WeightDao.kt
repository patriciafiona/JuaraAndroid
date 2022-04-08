package com.patriciafiona.tentangku.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.patriciafiona.tentangku.data.source.local.entity.Weight

@Dao
interface WeightDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(weight: Weight)
    @Update
    fun update(weight: Weight)
    @Delete
    fun delete(weight: Weight)
    @Query("SELECT * from weight ORDER BY id ASC")
    fun getAllWeights(): LiveData<List<Weight>>
}