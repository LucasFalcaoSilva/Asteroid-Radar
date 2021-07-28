package com.udacity.asteroidradar.flow.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.databinding.RecyclerItemAsteroidBinding
import com.udacity.asteroidradar.domain.Asteroid

class AsteroidAdapter(
    private val clickListener: OnClickListener
) : ListAdapter<Asteroid, AsteroidViewHolder>(
    DiffCallback
) {
    companion object DiffCallback : DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid) =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid) =
            oldItem.id == newItem.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        AsteroidViewHolder(
            RecyclerItemAsteroidBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val marsProperty = getItem(position)
        holder.itemView.setOnClickListener {
            clickListener.onClick(marsProperty)
        }
        holder.bind(marsProperty)
    }
}

class AsteroidViewHolder(
    private var binding: RecyclerItemAsteroidBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(asteroid: Asteroid) {
        binding.apply {
            property = asteroid
            executePendingBindings()
        }
    }
}

class OnClickListener(val clickListener: (asteroid: Asteroid) -> Unit) {
    fun onClick(marsProperty: Asteroid) = clickListener(marsProperty)
}