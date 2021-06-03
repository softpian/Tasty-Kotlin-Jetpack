package com.softpian.tasty.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.softpian.tasty.databinding.ReviewsRowLayoutBinding
import com.softpian.tasty.models.yelp.Review
import com.softpian.tasty.utils.TastyDiffUtil

class ReviewAdapter : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    private var reviews = emptyList<Review>()

    class ReviewViewHolder(
        private val binding: ReviewsRowLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): ReviewViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ReviewsRowLayoutBinding.inflate(layoutInflater, parent, false)
                return ReviewViewHolder(binding)
            }
        }

        fun bind(review: Review) {
            binding.review = review
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        return ReviewViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(reviews[position])
    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    fun setData(newReviews: List<Review>) {
        val diffUtilCallback = TastyDiffUtil(reviews, newReviews)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtilCallback)
        reviews = newReviews
        diffUtilResult.dispatchUpdatesTo(this)
    }
}