package com.amit.nasaa.pod.ui.main.repository

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.amit.nasaa.pod.ui.main.database.APODEntity
import com.amit.nasaa.pod.ui.main.database.APODRoomDb
import com.amit.nasaa.pod.ui.main.model.Result
import com.amit.nasaa.pod.ui.main.network.APODApi
import com.amit.nasaa.pod.ui.main.network.KEY
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class APODRepository(private val application: Application) {

    private val dao = APODRoomDb.getDatabase(application).dao()
    private val retrofitService = APODApi.retrofitService

    private var apodModel: MutableLiveData<Result> = MutableLiveData()

    private val format = "yyyy-MM-dd"

    private val ERROR_CODE_NETWORK_FAILURE = 400
    private val ERROR_CODE_NO_NETWORK = 401

    fun getApodData() : MutableLiveData<Result>{
        return apodModel
    }
    suspend fun getAPOD() {

        val date: String = SimpleDateFormat(format, Locale.getDefault()).format(Date())

        //if current date equals db date then fetch from db
        val savedData = dao.getAPOD()
        if(savedData!= null && TextUtils.equals(savedData.date, date)){
            //we have todays APOD in db, no need to fetch again
            postDataToUI(savedData)
            return
        }

        if (isNetworkAvailable(application)) {

            val response = retrofitService.getAPOD(KEY)

            if (response.isSuccessful) {
                val body = response.body()
                body?.apply {
                    val entity = APODEntity(
                        body.copyright, body.date, body.explanation, body.hdurl, body.media_type,
                        body.service_version, body.title, body.url
                    )
                    dao.deleteAll()
                    dao.insert(entity)
                    postDataToUI(entity)
                }
            } else {
                postErroToUI(ERROR_CODE_NETWORK_FAILURE, savedData)
            }
        } else {
            postErroToUI(ERROR_CODE_NO_NETWORK, savedData)
        }
    }

    private fun postDataToUI(entity: APODEntity){
        CoroutineScope(Dispatchers.Main).launch {
            apodModel.postValue(Result.Success(entity))
        }
    }

    private fun postErroToUI(errorCode : Int, entity: APODEntity){
        CoroutineScope(Dispatchers.Main).launch {
            apodModel.postValue(Result.Failure(errorCode, entity))
        }
    }
    private fun isNetworkAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

}