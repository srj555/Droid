package com.sr.myapplication.core.network

import com.sr.myapplication.core.app.AppController
import com.sr.myapplication.module.home.model.DataModel
import com.sr.myapplication.module.home.model.DataRepoModel
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class DataRepository {
    @JvmField
    @Inject
    var webService: RetrofitAPIInterface? = null
    val list: Observable<DataRepoModel>
        get() {
            return (Observable.defer {
                Observable.create<DataRepoModel> {
                    emitter ->
                    AppController.appComponent?.inject(this)
                    val call = webService!!.retrieveList()
                    call!!.enqueue(object : Callback<ArrayList<DataModel?>> {
                        override fun onResponse(
                            call: Call<ArrayList<DataModel?>>,
                            response: Response<ArrayList<DataModel?>>
                        ) {
                            response.body()?.let {
                                emitter.onNext(DataRepoModel(it))
                                emitter.onComplete()
                            }
                        }
                        override fun onFailure(
                            call: Call<ArrayList<DataModel?>>,
                            t: Throwable
                        ) {
                            emitter.onError(t)
                            emitter.onComplete()
                        }
                    })

                }
            } as? Observable<DataRepoModel>?)!!
        }

}