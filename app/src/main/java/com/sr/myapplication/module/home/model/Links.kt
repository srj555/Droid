package com.sr.myapplication.module.home.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Links(
    @SerializedName("patch") var patch: Patch? = Patch(),
    @SerializedName("reddit") var reddit: Reddit? = Reddit(),
    @SerializedName("flickr") var flickr: Flickr? = Flickr(),
    @SerializedName("presskit") var presskit: String? = null,
    @SerializedName("webcast") var webcast: String? = null,
    @SerializedName("youtube_id") var youtubeId: String? = null,
    @SerializedName("article") var article: String? = null,
    @SerializedName("wikipedia") var wikipedia: String? = null

): Parcelable