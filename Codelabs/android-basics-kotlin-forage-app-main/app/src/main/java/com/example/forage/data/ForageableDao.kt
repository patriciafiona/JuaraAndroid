/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.forage.data

import android.content.ClipData
import androidx.room.*
import com.example.forage.model.Forageable
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for database interaction.
 */
@Dao
interface ForageableDao {

    // TODO: implement a method to retrieve all Forageables from the database
    @Query("SELECT * from Forageable")
    fun getForageables(): Flow<List<Forageable>>

    // TODO: implement a method to retrieve a Forageable from the database by id
    @Query("SELECT * from Forageable WHERE id = :id")
    fun getForageable(id: Long):Flow<Forageable>

    // TODO: implement a method to insert a Forageable into the database
    //  (use OnConflictStrategy.REPLACE)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Forageable)

    // TODO: implement a method to update a Forageable that is already in the database
    @Update
    suspend fun update(item: Forageable)

    // TODO: implement a method to delete a Forageable from the database.
    @Delete
    suspend fun delete(item: Forageable)
}
