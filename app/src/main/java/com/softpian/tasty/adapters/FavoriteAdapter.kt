package com.softpian.tasty.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.softpian.tasty.data.database.entities.TastyFavoriteEntity
import com.softpian.tasty.databinding.FavoriteRowLayoutBinding
import com.softpian.tasty.models.yelp.Business
import com.softpian.tasty.utils.TastyDiffUtil

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private var favorites = emptyList<TastyFavoriteEntity>()

    class FavoriteViewHolder(
        private val binding: FavoriteRowLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): FavoriteViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FavoriteRowLayoutBinding.inflate(layoutInflater, parent, false)
                return FavoriteViewHolder(binding)
            }
        }

        fun bind(business: Business) {
            binding.business = business
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(favorites[position].business)
    }

    override fun getItemCount(): Int {
        return favorites.size
    }

    fun setData(newFavorites: List<TastyFavoriteEntity>) {
        val diffUtilCallback = TastyDiffUtil(favorites, newFavorites)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtilCallback)
        favorites = newFavorites
        diffUtilResult.dispatchUpdatesTo(this)
    }
}