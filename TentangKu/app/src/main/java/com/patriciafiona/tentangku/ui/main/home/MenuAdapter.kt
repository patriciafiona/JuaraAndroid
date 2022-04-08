package com.patriciafiona.tentangku.ui.main.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.patriciafiona.tentangku.R
import com.patriciafiona.tentangku.data.source.local.entity.Menu

class MenuAdapter(private val listMenu: ArrayList<Menu>): RecyclerView.Adapter<MenuAdapter.MenuHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_main_menu, parent, false)
        return MenuHolder(view)
    }

    override fun onBindViewHolder(holder: MenuHolder, position: Int) {
        val (name, photo) = listMenu[position]
        holder.imgPhoto.setImageResource(photo)
        holder.tvName.text = name

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listMenu[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int = listMenu.size

    class MenuHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgPhoto: ImageView = itemView.findViewById(R.id.menu_img)
        var tvName: TextView = itemView.findViewById(R.id.menu_txt)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Menu)
    }
}