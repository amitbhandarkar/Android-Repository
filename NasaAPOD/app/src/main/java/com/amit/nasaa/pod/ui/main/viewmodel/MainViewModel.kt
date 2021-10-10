package com.amit.nasaa.pod.ui.main.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.amit.nasaa.pod.ui.main.model.Result
import com.amit.nasaa.pod.ui.main.repository.APODRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainViewModel(application: Application) : AndroidViewModel(application) {
    private var apodModel: LiveData<Result>? = null

    init {

        val repo = APODRepository(application)
        apodModel = repo.getApodData()
        CoroutineScope(Dispatchers.IO).launch {
            repo.getAPOD()
        }

    }

    fun getApod(): LiveData<Result>? {
        return apodModel
    }
}