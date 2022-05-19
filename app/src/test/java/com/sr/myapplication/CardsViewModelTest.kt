package com.sr.myapplication


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.sr.myapplication.module.home.model.DataModel
import com.sr.myapplication.module.home.model.DataRepoModel
import com.sr.myapplication.core.network.DataRepository
import com.sr.myapplication.core.network.RetrofitAPIInterface
import com.sr.myapplication.module.home.viewmodel.CardsListViewModel
import junit.framework.Assert.assertEquals
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.InjectMocks
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
    @InjectMocks
    var viewModel: CardsListViewModel? = null

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
        viewModel = mock(CardsListViewModel::class.java)
        repository = mock(DataRepository::class.java)
    }

    private fun getJson(path: String): String {
        val uri = this.javaClass.classLoader?.getResource(path)
        val file = File(uri?.path ?: "")
        return String(file.readBytes())
    }

    @Test
    fun fetchList() {
        val dataModel = arrayListOf(Mockito.spy(DataModel::class.java))
        val dataRepoModel = DataRepoModel(dataModel)
        val data = MutableLiveData<DataRepoModel>()
        data.postValue(dataRepoModel)

        //Setting how up the mock behaves
        Mockito.doReturn(data).`when`(viewModel)?.getListLiveData()
        // fetchList
        viewModel?.fetchList()

        Assert.assertEquals(data, viewModel?.getListLiveData())
    }

    @Test
    @Throws(IOException::class)
    fun testApiSuccess() {
        mockWebServer?.enqueue(
            MockResponse().setBody(getJson("spacex_getList.json")
            )
        )
        val call= service!!.retrieveList()
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
        val call= service!!.retrieveList()
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
