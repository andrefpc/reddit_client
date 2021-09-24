package com.andrefpc.extensions

import android.content.res.Configuration
import android.webkit.WebView
import com.andrefpc.data.RedditEmbed


object WebViewExtensions {
    /**
     * Load an embed iframe into a WebView
     * @param embed The params to be embed
     */
    fun WebView.loadEmbed(embed: RedditEmbed) {
        this.setInitialScale(1)
        this.settings.allowFileAccess = true
        this.settings.javaScriptEnabled = true
        this.settings.loadWithOverviewMode = true
        this.settings.useWideViewPort = true

        var backgroundParam = ""
        val nightModeFlags = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        if(nightModeFlags == Configuration.UI_MODE_NIGHT_YES){
            backgroundParam = "background: black;"
        }

        val dataHtml =
            "<!DOCTYPE html><html> <head> <meta charset=\"UTF-8\"><meta name=\"viewport\" content=\"target-densitydpi=high-dpi\" /> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"> <link rel=\"stylesheet\" media=\"screen and (-webkit-device-pixel-ratio:1.5)\" href=\"hdpi.css\" /></head> <body style=\"margin:0 0 0 0; padding:0 0 0 0; $backgroundParam\"> <p align=\"center\"> <iframe width='${embed.width}' height='${embed.height}' src=\"${embed.url}\" frameborder=\"0\"></iframe> </p> </body> </html> "
        this.loadDataWithBaseURL(embed.providerUrl, dataHtml, "text/html", "UTF-8", null)
    }
}
