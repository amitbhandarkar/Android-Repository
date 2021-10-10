package com.amit.nasaa.pod.ui.main.model

import com.amit.nasaa.pod.ui.main.database.APODEntity

sealed class Result {
    data class Success(val data : APODEntity) : Result()
    data class Failure(val errorCode: Int, val data : APODEntity? = null) : Result()
}
/*

sealed class Result <T>(val response: APODEntity?=null, val errorCode: Int?=null) {
    data class Success<T>(val response : APODEntity) : Result<T>(response)
    data class Failure<T>(val errorCode: Int) : Result<T>(errorCode)
}*/
