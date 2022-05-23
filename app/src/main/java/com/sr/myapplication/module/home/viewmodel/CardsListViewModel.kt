package com.sr.myapplication.module.home.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sr.myapplication.core.app.AppController
import com.sr.myapplication.core.base.BaseSchedulerProvider
import com.sr.myapplication.core.base.BaseViewModel
import com.sr.myapplication.core.network.DataRepository
import com.sr.myapplication.module.home.model.DataRepoModel
import javax.inject.Inject


class CardsListViewModel : BaseViewModel() {
    private var listLiveData = MutableLiveData<DataRepoModel>()

    @JvmField
    var isLoading = ObservableBoolean()

    @JvmField
    var isError = ObservableBoolean()

    private lateinit var schedulers: BaseSchedulerProvider

    fun init(schedulerProvider: BaseSchedulerProvider) {
        AppController.appComponent?.inject(this)
        schedulers = schedulerProvider
    }

    @JvmField
    @Inject
    var repository: DataRepository? = null
    fun fetchList() {
        isLoading.set(true)
        val listObservable = repository?.getList()
        compositeDisposable.add(listObservable
            ?.subscribeOn(schedulers.io())
            ?.map { it }
            ?.observeOn(schedulers.ui())
            ?.subscribe(
                { data ->

                    // Update live data
                    listLiveData.value = data
                    isLoading.set(false)
                },
                { throwable ->
                    // Update live data
                    val response = DataRepoModel()
                    response.throwable = throwable
                    listLiveData.value = response
                    isLoading.set(false)
                }
            ))
    }

    fun getListLiveData(): LiveData<DataRepoModel?> {
        return listLiveData
    }

    fun onClear() {
        super.onCleared()
    }

}