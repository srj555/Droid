package com.sr.myapplication.core.di

import com.sr.myapplication.core.network.DataRepository
import com.sr.myapplication.module.home.viewmodel.CardsListViewModel
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {
    fun inject(dataRepository: DataRepository?)
    fun inject(cardsListViewModel: CardsListViewModel?)
}