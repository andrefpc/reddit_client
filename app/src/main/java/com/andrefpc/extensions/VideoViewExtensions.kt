package com.andrefpc.extensions

import android.net.Uri
import com.andrefpc.widget.CustomVideoView


object VideoViewExtensions {
    /**
     * Load a video into the CustomVideoView from a remote video URL
     * @param url The url of the remote video
     * @param width The width of the image
     * @param height The height of the image
     */
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
