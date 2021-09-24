package com.andrefpc.extensions

import android.net.Uri
import com.andrefpc.widget.CustomVideoView


object VideoViewExtensions {
    fun CustomVideoView.loadVideo(url: String, width: Int, height: Int) {
        try {
            val video: Uri = Uri.parse(url)
            this.setVideoURI(video)
            this.requestFocus()
            this.setOnPreparedListener { mp ->
                this.setVideoSize(width, height)
                mp.setVolume(0.90f, 0.90f)
                this.start()
            }
            this.setOnCompletionListener {
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
