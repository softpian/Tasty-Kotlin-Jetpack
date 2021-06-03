package com.softpian.tasty.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.softpian.tasty.databinding.RestaurantRowLayoutBinding
import com.softpian.tasty.models.yelp.Business
import com.softpian.tasty.utils.TastyDiffUtil

class RestaurantAdapter : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

    private var restaurants = emptyList<Business>()

    class RestaurantViewHolder(
        private val binding: RestaurantRowLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): RestaurantViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RestaurantRowLayoutBinding.inflate(layoutInflater, parent, false)
                return RestaurantViewHolder(binding)
            }
        }

        fun bind(business: Business) {
            binding.business = business
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        return RestaurantViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bind(restaurants[position])
    }

    override fun getItemCount(): Int {
        return restaurants.size
    }

    fun setData(businesses: List<Business>) {
        val diffUtilCallback = TastyDiffUtil(restaurants, businesses)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtilCallback)
        restaurants = businesses
        diffUtilResult.dispatchUpdatesTo(this)
    }
}