package com.sr.myapplication.module.detail.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sr.myapplication.R
import com.sr.myapplication.core.app.ApiConstants.BUNDLE_KEY_DATA_MODEL
import com.sr.myapplication.module.home.model.DataModel

class CardsDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_cards)
        val dataModel = intent.getParcelableExtra<DataModel>(BUNDLE_KEY_DATA_MODEL)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CardDetailFragment.newInstance(dataModel))
                .commitNow()
        }
    }
}