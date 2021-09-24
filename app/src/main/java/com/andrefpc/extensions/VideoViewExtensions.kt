package com.andrefpc.extensions

import android.net.Uri
import android.view.View
import com.andrefpc.widget.CustomVideoView


object VideoViewExtensions {
    fun CustomVideoView.loadVideo(url: String, width: Int, height: Int) {
        try {
            val video: Uri = Uri.parse(url)
            this.setVideoURI(video)
            this.start()
            this.setOnPreparedListener { mp ->
                this.setVideoSize(width, height)
                mp.setVolume(0.90f, 0.90f)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
