package com.andrefpc.util.image

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.provider.MediaStore
import com.bumptech.glide.Glide
import java.io.File
import java.io.OutputStream
import java.util.*

class ImageUtilImpl(
    private val context: Context
) : ImageUtil {
    /**
     * Download a image using glide and call the saveImage method sending the downloaded bitmap
     * @param url Image Url
     */
    override suspend fun saveImageOnGallery(url: String) {
        val bitmap = Glide.with(context).asBitmap().load(url).submit().get()
        saveImage(bitmap)
    }

    /**
     * Create the image file from a bitmap
     * @param image Image Bitmap downloaded from Glide
     */
    private fun saveImage(image: Bitmap?) {
        val values = ContentValues()
        val fileName = "reddit_client_" + Date().time + ".jpg"
        values.put(MediaStore.Images.Media.TITLE, fileName)
        values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis())
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
        }

        context.contentResolver?.let { contentResolver ->
            val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val uri = contentResolver.insert(contentUri, values)
            uri?.let {
                if (image != null) {
                    val imageOut: OutputStream? = contentResolver.openOutputStream(it)
                    imageOut.use { out ->
                        image.compress(Bitmap.CompressFormat.JPEG, 100, out)
                    }
                } else {
                    contentResolver.delete(it, null, null)
                }
                it.path?.let { path -> galleryAddPic(path) }
            }
        }

    }

    /**
     * Tell to gallery that have a new image saved on the phone
     * @param imagePath Path of the generated image
     */
    private fun galleryAddPic(imagePath: String) {
        val file = File(imagePath)
        MediaScannerConnection.scanFile(
            context, arrayOf(file.toString()),
            null, null
        )
    }
}