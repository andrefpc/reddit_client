package com.andrefpc.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.andrefpc.data.RedditData
import com.andrefpc.databinding.ContentLayoutBinding
import com.andrefpc.extensions.ImageViewExtensions.loadImage
import com.andrefpc.extensions.VideoViewExtensions.loadVideo
import com.andrefpc.extensions.WebViewExtensions.loadEmbed
import androidx.core.content.ContextCompat.startActivity

import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import com.andrefpc.extensions.StringExtensions.isImageURL
import com.andrefpc.extensions.StringExtensions.isValidUrl


class CustomContentView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding: ContentLayoutBinding =
        ContentLayoutBinding.inflate(LayoutInflater.from(context), this, true)

    fun setupLayout(data: RedditData){
        binding.contentSubreddit.text = data.getPostedBy()
        binding.contentTitle.text = data.title
        if(data.media?.video != null){
            val video = data.media.video!!
            binding.contentVideo.loadVideo(video.url, video.width, video.height)
            binding.contentFlipper.displayedChild = VIDEO_LAYOUT
            return
        }

        if(data.media?.embed != null){
            val embed = data.media.embed!!
            binding.contentEmbed.loadEmbed(embed)
            binding.contentFlipper.displayedChild = EMBED_LAYOUT
            return
        }

        if(data.postHint == "image" || data.url.isImageURL()){
            binding.contentImage.loadImage(data.url) {
                binding.contentImage.loadImage(data.thumbnail)
            }
            binding.contentFlipper.displayedChild = IMAGE_LAYOUT
            return
        }

        if(data.postHint == "link" || (data.postHint == null && data.url.isValidUrl())){
            binding.contentLink.text = data.url
            binding.contentFlipper.displayedChild = LINK_LAYOUT
            binding.contentLink.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(data.url))
                context.startActivity(browserIntent)
            }
        }

    }

    companion object {
        const val IMAGE_LAYOUT = 0
        const val VIDEO_LAYOUT = 1
        const val EMBED_LAYOUT = 2
        const val LINK_LAYOUT = 3
    }
}