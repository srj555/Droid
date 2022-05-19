package com.sr.myapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.sr.myapplication.module.home.model.DataModel
import com.sr.myapplication.module.home.model.DataRepoModel
import com.sr.myapplication.core.network.DataRepository
import com.sr.myapplication.core.network.RetrofitAPIInterface
import io.reactivex.rxjava3.core.Observable
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class DataRepoTest {
    @Mock
    var webService: RetrofitAPIInterface? = null

    @InjectMocks
    var dataRepository: DataRepository? = null

    @get:Rule
    var instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        dataRepository = Mockito.mock(DataRepository::class.java)
    }

    //Setting how up the mock behaves
    @Test
    fun getList(){
            val dataModel = arrayListOf(Mockito.spy(DataModel::class.java))
            val dataRepoModel = DataRepoModel(dataModel)
            val data = getListMock(dataRepoModel)

            //Setting how up the mock behaves
            Mockito.doReturn(data).`when`(dataRepository)?.getList()
            webService?.retrieveList()
            Mockito.verify(webService)?.retrieveList()
            Assert.assertEquals(data, dataRepository?.getList())
        }

    fun getListMock(dataRepoModel:DataRepoModel): Observable<DataRepoModel> {
        return (Observable.defer {
            Observable.create<DataRepoModel> { emitter ->
                emitter.onNext(dataRepoModel)
                emitter.onComplete()
            }
        } as? Observable<DataRepoModel>?)!!
    }
}