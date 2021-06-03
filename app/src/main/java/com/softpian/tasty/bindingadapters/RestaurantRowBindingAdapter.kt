package com.softpian.tasty.bindingadapters

import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.softpian.tasty.models.yelp.Business
import com.softpian.tasty.ui.restaurant.RestaurantFragmentDirections
import com.softpian.tasty.utils.Utils

class RestaurantRowBindingAdapter {

    companion object {

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, imageUrl: String) {
            imageView.load(imageUrl) {
                crossfade(700)
            }
        }

        @BindingAdapter("putDistanceInKm")
        @JvmStatic
        fun putDistanceInKm(textView: TextView, distance: Double) {
            val distanceInKm = distance / 1000.0
            textView.text = String.format("%.2f", distanceInKm)
        }

        @BindingAdapter("onClickListener")
        @JvmStatic
        fun onClickListener(restaurantRowLayout: ConstraintLayout, business: Business) {
            restaurantRowLayout.setOnClickListener {
                if (Utils.isNetworkAvailable(restaurantRowLayout.context)) {
                    val navDirections =
                        RestaurantFragmentDirections.actionRestaurantFragmentToBusinessDetailsActivity(
                            business
                        )
                    restaurantRowLayout.findNavController().navigate(navDirections)
                } else {
                    Toast.makeText(
                        restaurantRowLayout.context,
                        "No Internet connection",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}