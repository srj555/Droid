package com.sr.myapplication.module.home.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Patch(
    @SerializedName("small") var small: String? = null,
    @SerializedName("large") var large: String? = null
):Parcelable