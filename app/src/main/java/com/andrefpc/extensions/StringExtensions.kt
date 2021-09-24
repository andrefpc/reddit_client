package com.andrefpc.extensions

import android.net.Uri
import com.andrefpc.widget.CustomVideoView


object StringExtensions {
    fun String.isImageURL(): Boolean {
        return if(this.isValidUrl()){
            this.contains(Regex("\\.(jpeg|jpg|gif|png)$"))
        }else{
            false
        }
    }

    fun String.isValidUrl(): Boolean {
        return this.contains(Regex("(http|https)://www"))
    }
}
