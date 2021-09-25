package com.andrefpc.extensions

import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.andrefpc.extensions.ViewExtensions.blink
import com.andrefpc.extensions.ViewExtensions.stopBlink
import com.andrefpc.util.AnimationSingleton

object ViewExtensions {
    /**
     * Start a blink animation for a View
     */
    fun View.blink(): Animation? {
        this.alpha = 1.0f
        this.visibility = View.VISIBLE
        return if(AnimationSingleton.animationEnabled) {
            val anim: Animation = AlphaAnimation(0.0f, 1.0f)
            anim.duration = 1000
            anim.startOffset = 0
            anim.repeatMode = Animation.REVERSE
            anim.repeatCount = Animation.INFINITE
            this.startAnimation(anim)
            anim
        }else {
            null
        }
    }

    /**
     * Stop the blink animation and hide the View
     */
    fun View.stopBlink(anim: Animation? = null) {
        anim?.cancel()
        this.alpha = 0.0f
        this.visibility = View.GONE
    }
}
