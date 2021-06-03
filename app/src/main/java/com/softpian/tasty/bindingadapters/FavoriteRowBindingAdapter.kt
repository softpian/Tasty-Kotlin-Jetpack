package com.softpian.tasty.bindingadapters

import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.softpian.tasty.models.yelp.Business
import com.softpian.tasty.ui.favorite.FavoriteFragmentDirections
import com.softpian.tasty.ui.restaurant.RestaurantFragmentDirections
import com.softpian.tasty.utils.Utils

class FavoriteRowBindingAdapter {

    companion object {

        @BindingAdapter("favoriteLoadImageFromUrl")
        @JvmStatic
        fun favoriteLoadImageFromUrl(imageView: ImageView, imageUrl: String) {
            imageView.load(imageUrl) {
                crossfade(700)
            }
        }

        @BindingAdapter("favoritePutDistanceInKm")
        @JvmStatic
        fun favoritePutDistanceInKm(textView: TextView, distance: Double) {
            val distanceInKm = distance / 1000.0
            textView.text = String.format("%.2f", distanceInKm)
        }

        @BindingAdapter("favoriteOnClickListener")
        @JvmStatic
        fun favoriteOnClickListener(favoriteRowLayout: ConstraintLayout, business: Business) {
            favoriteRowLayout.setOnClickListener {
                if (Utils.isNetworkAvailable(favoriteRowLayout.context)) {
                    val navDirections =
                            FavoriteFragmentDirections.actionFavoriteFragmentToBusinessDetailsActivity(
                                    business
                            )
                    favoriteRowLayout.findNavController().navigate(navDirections)
                } else {
                    Toast.makeText(
                            favoriteRowLayout.context,
                            "No Internet connection",
                            Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}