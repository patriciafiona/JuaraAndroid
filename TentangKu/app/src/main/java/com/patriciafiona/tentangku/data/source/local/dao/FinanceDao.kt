package com.patriciafiona.tentangku.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.patriciafiona.tentangku.data.source.local.entity.FinanceTransaction


@Dao
interface FinanceDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(transaction: FinanceTransaction)
    @Update
    fun update(transaction: FinanceTransaction)
    @Delete
    fun delete(transaction: FinanceTransaction)
    @Query("SELECT * from financetransaction ORDER BY id ASC")
    fun getAllTransaction(): LiveData<List<FinanceTransaction>>
}