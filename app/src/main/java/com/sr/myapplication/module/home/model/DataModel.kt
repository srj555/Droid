package com.sr.myapplication.module.home.model

import com.google.gson.annotations.SerializedName

data class DataModel(
    var name: String? = null,
    var flight_number: String? = null,
    @SerializedName("links") var links: Links? = Links(),
) {
    var isImgLoadFailed = false

    // TODO dummy image url
    var imageHref: String? =
        "https://cms.qz.com/wp-content/uploads/2018/02/spacex-falcon-heavy-elon-musk-china-europe-esa-nasa-mars-sls-boeing.jpg?quality=75&strip=all&w=3200&h=1800"
}