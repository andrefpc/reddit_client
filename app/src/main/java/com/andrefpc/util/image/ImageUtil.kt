package com.andrefpc.util.image

interface ImageUtil {
    suspend fun saveImageOnGallery(url: String)
}