package com.example.tentanganak.adapter

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tentanganak.R
import com.example.tentanganak.model.ModelModul

class InfoAdapter(private val list : ArrayList<ModelModul>) : RecyclerView.Adapter<InfoAdapter.Viewholder>() {

    private var onClickListener : OnClickListener? = null


    fun setOnClickListener(onClickListener : OnClickListener){
        this.onClickListener = onClickListener
    }

    interface OnClickListener{
        fun onClick(position: Int,model : ModelModul)
    }

    class Viewholder(itemview : View) : RecyclerView.ViewHolder(itemview) {
        val avatar = itemview.findViewById<ImageView>(R.id.image_avatar)
        val judul = itemview.findViewById<TextView>(R.id.judul)
        val desc = itemview.findViewById<TextView>(R.id.desc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_info,parent,false)
        return Viewholder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val items = list[position]

        holder.judul.text = items.judul.toString()
        holder.desc.text = items.desc.toString()
        Glide.with(holder.avatar)
            .load(items.avatar)
            .into(holder.avatar)

        holder.itemView.setOnClickListener {
            if (onClickListener != null){
                onClickListener!!.onClick(position,items)
            }
        }
    }
}