package com.sr.myapplication.module.home.model

import com.google.gson.annotations.SerializedName


data class Reddit(

    @SerializedName("campaign") var campaign: String? = null,
    @SerializedName("launch") var launch: String? = null,
    @SerializedName("media") var media: String? = null,
    @SerializedName("recovery") var recovery: String? = null

)