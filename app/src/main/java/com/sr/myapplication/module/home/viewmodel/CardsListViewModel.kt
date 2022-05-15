package com.sr.myapplication.module.home.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sr.myapplication.core.app.AppController
import com.sr.myapplication.module.home.model.DataRepoModel
import com.sr.myapplication.core.network.DataRepository
import javax.inject.Inject

class CardsListViewModel : ViewModel() {
    lateinit var listObservable: MutableLiveData<DataRepoModel>
    @JvmField
    var isLoading = ObservableBoolean()
    @JvmField
    var isError = ObservableBoolean()

    @JvmField
    @Inject
    var repository: DataRepository? = null
    fun fetchList() {
        isLoading.set(true)
        AppController.appComponent?.inject(this)
        listObservable = repository?.list!!
    }

    fun getListObservable(): LiveData<DataRepoModel?>? {
        return listObservable
    }
}