package com.softpian.tasty.bindingadapters

import android.content.Intent
import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.load
import com.softpian.tasty.R

class DetailsBindingAdapter {

    companion object {

        @BindingAdapter("loadDetailsImageFromUrl")
        @JvmStatic
        fun loadDetailsImageFromUrl(imageView: ImageView, imageUrl: String?) {
            imageUrl?.let {
                imageView.load(it) {
                    crossfade(700)
                }
            }
        }

        @BindingAdapter("isOpenNow")
        @JvmStatic
        fun isOpenNow(textView: TextView, isOpenNow: Boolean) {
            if (isOpenNow) {
                textView.text = "Open now"
                textView.setTextColor(ContextCompat.getColor(textView.context, R.color.lightGreen))
            } else {
                textView.text = "Closed now"
                textView.setTextColor(ContextCompat.getColor(textView.context, R.color.lightRed))
            }
        }

        @BindingAdapter("putWholeAddress")
        @JvmStatic
        fun putWholeAddress(textView: TextView, displayAddress: List<String>?) {
            var wholeAddress: String = ""
            displayAddress?.let {
                for (address in it) {
                    wholeAddress += address
                }
                textView.text = wholeAddress
            }
        }

        @BindingAdapter("setStartOfOpeningHour", "setEndOfOpeningHour", requireAll = true)
        @JvmStatic
        fun setOpeningHours(textView: TextView, startOpeningHour: String?, endOpeningHour: String?) {

            var strStartOpeningHour = ""
            startOpeningHour?.let {
                val startHour = Integer.parseInt(it.substring(0, 2))
                val startMinute = it.substring(2)
                val startHourInAM = if (startHour / 12 > 0) startHour % 12 else startHour
                val startTwelveHourNotation = if (startHour < 12) "AM" else "PM"
                strStartOpeningHour = "$startHourInAM:$startMinute $startTwelveHourNotation"
            }

            var strEndOpeningHour = ""
            endOpeningHour?.let {
                val endHour = Integer.parseInt(it.substring(0, 2))
                val endMinute = it.substring(2)
                val endHourInAM = if (endHour / 12 > 0) endHour % 12 else endHour
                val endTwelveHourNotation = if (endHour < 12) "AM" else "PM"
                strEndOpeningHour = "$endHourInAM:$endMinute $endTwelveHourNotation"
            }

            textView.text = "$strStartOpeningHour ~ $strEndOpeningHour"
        }
    }
}