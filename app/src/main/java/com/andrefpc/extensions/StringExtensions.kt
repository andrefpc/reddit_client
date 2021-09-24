package com.andrefpc.extensions


object StringExtensions {
    /**
     * Verify if the text is an image URL
     * @return [Boolean]
     */
    fun String.isImageURL(): Boolean {
        return if (this.isValidUrl()) {
            this.contains(Regex("\\.(jpeg|jpg|gif|png)$"))
        } else {
            false
        }
    }

    /**
     * Verify if the text is a valid URL
     * @return [Boolean]
     */
    fun String.isValidUrl(): Boolean {
        return this.contains(Regex("(http|https)://www"))
    }
}
