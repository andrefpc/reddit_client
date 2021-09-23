package com.andrefpc.extensions

import android.animation.ValueAnimator
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.LinearInterpolator

object ViewExtensions {
    fun View.blink() {
        val anim: Animation = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 1000
        anim.startOffset = 0
        anim.repeatMode = Animation.REVERSE
        anim.repeatCount = Animation.INFINITE
        this.startAnimation(anim)
    }

    fun View.stopBlink() {
        this.alpha = 0.0f
    }

    fun View.collapseHorizontal() {
        val initialWidth = this.measuredWidth
        val valueAnimator = ValueAnimator.ofFloat(0f, 1f)
        valueAnimator.addUpdateListener {
            val value = it.animatedValue as Float
            if (value == 1f) {
                this@collapseHorizontal.visibility = View.GONE
            } else {
                this@collapseHorizontal.layoutParams.width =
                    initialWidth - (initialWidth * value).toInt()
                this@collapseHorizontal.requestLayout()
            }
        }

        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.duration = 100L
        valueAnimator.start()
    }
}
