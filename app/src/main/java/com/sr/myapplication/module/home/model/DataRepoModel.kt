package com.sr.myapplication.module.home.model

class DataRepoModel {
    lateinit var dataModel: ArrayList<DataModel?>
    var throwable: Throwable? = null

    constructor(dataModel: ArrayList<DataModel?>) {
        this.dataModel = dataModel
    }

    constructor(throwable: Throwable?) {
        this.throwable = throwable
    }

}