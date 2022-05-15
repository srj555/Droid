package com.sr.myapplication.core.network

import com.sr.myapplication.module.home.model.DataModel
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitAPIInterface {
    @GET("launches/")
    fun retrieveList(): Call<ArrayList<DataModel?>>?
}