package com.andrefpc.extensions

import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.andrefpc.extensions.ViewExtensions.blink
import com.andrefpc.extensions.ViewExtensions.stopBlink

object ViewExtensions {
    fun View.blink() {
        this.alpha = 1.0f
        this.visibility = View.VISIBLE
        val anim: Animation = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 1000
        anim.startOffset = 0
        anim.repeatMode = Animation.REVERSE
        anim.repeatCount = Animation.INFINITE
        this.startAnimation(anim)
    }

    fun View.stopBlink() {
        this.alpha = 0.0f
        this.visibility = View.GONE
    }
}
