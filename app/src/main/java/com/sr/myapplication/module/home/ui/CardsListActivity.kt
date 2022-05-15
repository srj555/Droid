package com.sr.myapplication.module.home.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sr.myapplication.R

class CardsListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_cards)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CardListFragment.newInstance())
                .commitNow()
        }
    }
}