package com.sr.myapplication.module.home.adapter

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.sr.myapplication.module.home.model.DataModel

object DataBindingAdapter {
    @JvmStatic
    @BindingAdapter("android:imgSrc")
    fun setImageUri(view: ImageView, rowData: DataModel) {
        // Avoid loading previously failed image
        if (!rowData.isImgLoadFailed) {
            view.visibility = View.VISIBLE
            Glide.with(view.context)
                .load(rowData.links?.patch?.small)
                .into(view)
        } else {
            view.visibility = View.GONE
        }
    }
}