package com.gayathri.videplayercompose.ads

import android.content.Context
import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.ima.ImaAdsLoader
import androidx.media3.exoplayer.source.ads.AdsLoader
import javax.inject.Inject

class AdsLoaderProvider @Inject constructor(
    private val context: Context
) :
    AdsLoader.Provider {
    val adsConfig =
        MediaItem.AdsConfiguration.Builder(Uri.parse("https://pubads.g.doubleclick.net/gampad/ads?iu=/21775744923/external/single_ad_samples&sz=640x480&cust_params=sample_ct%3Dlinear&ciu_szs=300x250%2C728x90&gdfp_req=1&output=vast&unviewed_position_start=1&env=vp&impl=s&correlator="))
            .build()

    override fun getAdsLoader(adsConfiguration: MediaItem.AdsConfiguration): AdsLoader {
        val imaAdsLoader = ImaAdsLoader.Builder(context).build()
//        imaAdsLoader.setPlayer(player)
        return imaAdsLoader
    }

}

class AdsLoaderProviderNew @Inject constructor(
    private val context: Context,
    private val player: ExoPlayer
) :
    AdsLoader.Provider {
    val adsConfig =
        MediaItem.AdsConfiguration.Builder(Uri.parse("https://pubads.g.doubleclick.net/gampad/ads?iu=/21775744923/external/single_ad_samples&sz=640x480&cust_params=sample_ct%3Dlinear&ciu_szs=300x250%2C728x90&gdfp_req=1&output=vast&unviewed_position_start=1&env=vp&impl=s&correlator="))
            .build()

    override fun getAdsLoader(adsConfiguration: MediaItem.AdsConfiguration): AdsLoader {
        val imaAdsLoader = ImaAdsLoader.Builder(context).build()
        imaAdsLoader.setPlayer(player)
        return imaAdsLoader
    }

}
