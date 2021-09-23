package com.andrefpc.extensions

import androidx.appcompat.widget.AppCompatImageView
import com.andrefpc.R
import com.bumptech.glide.Glide

object ImageViewExtensions {
    fun AppCompatImageView.loadImage(url: String) {
        if (url.isNotEmpty()) {
            Glide.with(this).load(url).into(this)
        } else {
            this.setImageResource(R.drawable.ic_reddit)
        }
    }
}
