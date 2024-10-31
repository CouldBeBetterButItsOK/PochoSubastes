package com.dam.xevi.pochosubastes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dam.xevi.pochosubastes.R
import com.dam.xevi.pochosubastes.databinding.ItemRecyclerviewBinding
import com.dam.xevi.pochosubastes.models.SubastaObject
import com.squareup.picasso.Picasso


class SubastaAdapter(private val subastas: List<SubastaObject>, private val listener: (SubastaObject) -> Unit) : RecyclerView.Adapter<SubastaAdapter.SubastaViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubastaViewHolder {
            val binding = ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return SubastaViewHolder(binding)
        }

        override fun getItemCount(): Int {
            return subastas.size
        }

        override fun onBindViewHolder(holder: SubastaViewHolder, position: Int) {
            val currentItem = subastas[position]
            Picasso.get().load(currentItem.imatgeUrl).into(holder.binding.objImg)
            holder.binding.objName.text = currentItem.nom
            holder.binding.subDate.text = currentItem.dataSubasta.toString()
            if (currentItem.venut) {
                holder.binding.sold.visibility = View.VISIBLE
                holder.binding.SoldImg.visibility = View.VISIBLE
                holder.binding.price.text = currentItem.preuFinal.toString() + "€"
            } else holder.binding.price.text = currentItem.preuInici.toString() + "€"
            holder.binding.root.setOnClickListener { listener(currentItem) }
        }

        /*class SubastaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val imageView: ImageView = itemView.findViewById(R.id.objImg)
            val name: TextView = itemView.findViewById(R.id.objName)
            val date: TextView = itemView.findViewById(R.id.subDate)
            val sold: TextView = itemView.findViewById(R.id.sold)
            val soldImg: ImageView = itemView.findViewById(R.id.SoldImg)
            val price: TextView = itemView.findViewById(R.id.price)
        }*/
        class SubastaViewHolder(val binding: ItemRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root)
    }