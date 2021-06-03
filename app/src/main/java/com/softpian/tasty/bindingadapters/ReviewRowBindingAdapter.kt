package com.softpian.tasty.bindingadapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.softpian.tasty.R

class ReviewRowBindingAdapter {

    companion object {

        @BindingAdapter("loadUserImageFromUrl")
        @JvmStatic
        fun loadUserImageFromUrl(imageView: ImageView, imageUrl: String?) {
            imageUrl?.let {
                imageView.load(imageUrl) {
                    crossfade(700)
                    error(R.drawable.ic_face_placeholder)
                }
            }
        }
    }
}