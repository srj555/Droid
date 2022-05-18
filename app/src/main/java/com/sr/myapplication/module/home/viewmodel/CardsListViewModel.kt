package com.sr.myapplication.module.home.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sr.myapplication.core.app.AppController
import com.sr.myapplication.core.base.BaseViewModel
import com.sr.myapplication.module.home.model.DataRepoModel
import com.sr.myapplication.core.network.DataRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject


class CardsListViewModel : BaseViewModel() {
    private var listLiveData =  MutableLiveData<DataRepoModel>()
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
        val listObservable = repository?.getList()
        compositeDisposable.add(listObservable
            ?.subscribeOn(Schedulers.io())
            ?.map {it}
            ?.observeOn(mainThread())
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

    fun onClear(){
        super.onCleared()
    }

}