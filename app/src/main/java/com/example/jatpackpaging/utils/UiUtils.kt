package com.example.jatpackpaging.utils

import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable

object UiUtils {

    fun getShimmerDrawable(): ShimmerDrawable {
        val shimmer =
            Shimmer.AlphaHighlightBuilder()// The attributes for a ShimmerDrawable is set by this builder
                .setDuration(1800) // how long the shimmering animation takes to do one full sweep
                .setBaseAlpha(0.9f) //the alpha of the underlying children
                .setHighlightAlpha(0.8f) // the shimmer alpha amount
                .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                .setAutoStart(true)
                .build()

        // This is the placeholder for the imageView
        return ShimmerDrawable().apply {
            setShimmer(shimmer)
        }
    }
}