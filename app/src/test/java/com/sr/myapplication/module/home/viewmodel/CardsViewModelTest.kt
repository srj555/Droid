package com.sr.myapplication.module.home.viewmodel


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.sr.myapplication.core.base.BaseSchedulerProvider
import com.sr.myapplication.core.base.TrampolineSchedulerProvider
import com.sr.myapplication.core.network.DataRepository
import com.sr.myapplication.core.network.RetrofitAPIInterface
import com.sr.myapplication.module.home.model.DataModel
import com.sr.myapplication.module.home.model.DataRepoModel
import io.reactivex.rxjava3.core.Observable
import junit.framework.Assert.assertEquals
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.IOException


@RunWith(MockitoJUnitRunner::class)
class CardsListViewModelTest {
    private lateinit var viewModel: CardsListViewModel
    private lateinit var scheduler: BaseSchedulerProvider

    @Mock
    var repository: DataRepository? = null

    @get:Rule
    var instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()
    var mockWebServer: MockWebServer? = null
    var retrofit: Retrofit? = null
    var service: RetrofitAPIInterface? = null


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mockWebServer = MockWebServer()
        retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer?.url("").toString())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit?.create(RetrofitAPIInterface::class.java)
        scheduler = TrampolineSchedulerProvider()

        viewModel = CardsListViewModel()
        viewModel.init(scheduler)
        repository = mock(DataRepository::class.java)
        viewModel.repository = this.repository
    }

    private fun getJson(path: String): String {
        val uri = this.javaClass.classLoader?.getResource(path)
        val file = File(uri?.path ?: "")
        return String(file.readBytes())
    }

    @Test
    fun fetchListPositive() {
        val dataModel = arrayListOf(Mockito.spy(DataModel::class.java))
        val dataRepoModel = DataRepoModel(dataModel)
        val data = getListMock(dataRepoModel)
        val liveData = MutableLiveData<DataRepoModel>()
        liveData.postValue(dataRepoModel)

        //Setting how up the mock behaves
        Mockito.doReturn(data).`when`(repository)?.getList()

        viewModel.fetchList()

        Assert.assertEquals(liveData.value, viewModel.getListLiveData().value)
    }


    @Test
    fun fetchListNegative() {
        val dataModel = arrayListOf(Mockito.spy(DataModel::class.java))
        val dataRepoModel = DataRepoModel(dataModel)
        val data = getListMockNegative(dataRepoModel)
        val liveData = MutableLiveData<DataRepoModel>()
        liveData.postValue(dataRepoModel)
        //Setting how up the mock behaves
        Mockito.doReturn(data).`when`(repository)?.getList()

        viewModel.fetchList()
        Assert.assertNotNull(viewModel.getListLiveData().value?.throwable)
    }

    private fun getListMock(dataRepoModel: DataRepoModel): Observable<DataRepoModel> {
        return (Observable.defer {
            Observable.create<DataRepoModel> { emitter ->
                emitter.onNext(dataRepoModel)
                emitter.onComplete()
            }
        } as? Observable<DataRepoModel>?)!!
    }

    private fun getListMockNegative(dataRepoModel: DataRepoModel): Observable<DataRepoModel> {
        return (Observable.defer {
            Observable.create<DataRepoModel> { emitter ->
                emitter.onError(Throwable())
                emitter.onComplete()
            }
        } as? Observable<DataRepoModel>?)!!
    }

    @Test
    @Throws(IOException::class)
    fun testApiSuccess() {
        mockWebServer?.enqueue(
            MockResponse().setBody(
                getJson("spacex_getList.json")
            )
        )
        val call = service!!.retrieveList()
        val dataModel = call!!.execute()
        Assert.assertNotNull(dataModel)
        assertEquals("FalconSat", dataModel.body()?.get(0)?.name)
    }

    @Test
    @Throws(IOException::class)
    fun testApiFailure() {
        mockWebServer?.enqueue(
            MockResponse()
                .setResponseCode(404)
                .setBody("[]")
        )
        val call = service!!.retrieveList()
        val dataModel = call!!.execute()
        Assert.assertNull(dataModel.body())
    }

    @After
    fun tearDown() {
        //Finish web server
        try {
            mockWebServer?.shutdown()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
