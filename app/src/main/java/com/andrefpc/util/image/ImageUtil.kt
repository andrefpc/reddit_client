package com.andrefpc.util.image

interface ImageUtil {
    /**
     * Save images to gallery from image url
     * @param url Image Url
     */
    suspend fun saveImageOnGallery(url: String)
}