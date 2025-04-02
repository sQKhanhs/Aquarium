package org.hyperskill.aquarium

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class AnimalAdapter(val animals: List<Animal>): RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder>() {
    class AnimalViewHolder(view: View): RecyclerView.ViewHolder(view){
        val name: TextView = view.findViewById(R.id.tv_name)
        val image: ImageView = view.findViewById(R.id.image_view)
        val description: TextView = view.findViewById(R.id.tv_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        return AnimalViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_item, parent, false))
    }

    override fun getItemCount(): Int {
        return animals.size
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        val animal = animals[position]

        Picasso.get()
            .load(animal.picture)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.error)
            .into(holder.image)
        holder.name.text = animal.name
        holder.description.text = animal.description
    }
}