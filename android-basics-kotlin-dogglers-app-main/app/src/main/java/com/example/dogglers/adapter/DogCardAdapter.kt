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
package com.example.dogglers.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dogglers.R
import com.example.dogglers.data.DataSource
import com.example.dogglers.model.Dog

/**
 * Adapter to inflate the appropriate list item layout and populate the view with information
 * from the appropriate data source
 */
class DogCardAdapter(
    private val context: Context?,
    private val layout: Int
): RecyclerView.Adapter<DogCardAdapter.DogCardViewHolder>() {

    private val listDog:List<Dog> = DataSource.dogs

    /**
     * Initialize view elements
     */
    class DogCardViewHolder(private val view: View): RecyclerView.ViewHolder(view!!) {
        val dogImg: ImageView = view.findViewById(R.id.dog_img)
        val dogName: TextView = view.findViewById(R.id.dog_name)
        val dogAge: TextView = view.findViewById(R.id.dog_age)
        val dogHobbies: TextView = view.findViewById(R.id.dog_hobbies)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogCardViewHolder {
        val adapterLayout = if(layout == 3) {
            //Grid
            LayoutInflater.from(parent.context)
                .inflate(R.layout.grid_list_item, parent, false)
        }else {
            //Vertical or Horizontal
            LayoutInflater.from(parent.context)
                .inflate(R.layout.vertical_horizontal_list_item, parent, false)
        }

        return DogCardViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int = listDog.size

    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: DogCardAdapter.DogCardViewHolder, position: Int) {
        with(holder){
            val item = listDog[position]
            val resources = context?.resources

            dogImg.setImageDrawable(resources?.getDrawable( item.imageResourceId ))
            dogName.text = item.name
            dogAge.text = "Age: ${item.age}"
            dogHobbies.text = "Hobbies: ${item.hobbies}"
        }
    }
}
