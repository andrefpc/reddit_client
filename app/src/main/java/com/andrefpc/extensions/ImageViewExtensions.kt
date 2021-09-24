package com.andrefpc.extensions

import android.graphics.drawable.Drawable
import androidx.appcompat.widget.AppCompatImageView
import com.andrefpc.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

object ImageViewExtensions {
    fun AppCompatImageView.loadImage(url: String, returnError: () -> Unit = {}) {
        if (url.isNotEmpty()) {
            Glide.with(this).load(url).listener(
                object : RequestListener<Drawable?> {
                    override fun onLoadFailed(
                        exception: GlideException?,
                        model: Any,
                        target: Target<Drawable?>,
                        isFirstResource: Boolean
                    ): Boolean {
                        returnError()
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any,
                        target: Target<Drawable?>,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }
                }
            ).into(this)
        } else {
            this.setImageResource(R.drawable.ic_reddit)
        }
    }
}
