package com.andrefpc.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.VideoView

class CustomVideoView : VideoView {
    constructor(ctx: Context) : super(ctx)
    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)

    private var mVideoWidth = 0
    private var mVideoHeight = 0

    fun setVideoSize(width: Int, height: Int) {
        mVideoWidth = width
        mVideoHeight = height
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var width = getDefaultSize(mVideoWidth, widthMeasureSpec)
        var height = getDefaultSize(mVideoHeight, heightMeasureSpec)
        if (mVideoWidth > 0 && mVideoHeight > 0) {
            when {
                mVideoWidth * height > width * mVideoHeight -> {
                    height = width * mVideoHeight / mVideoWidth
                }
                mVideoWidth * height < width * mVideoHeight -> {
                    width = height * mVideoWidth / mVideoHeight
                }
                else -> {

                }
            }
        }
        setMeasuredDimension(width, height)
    }
}