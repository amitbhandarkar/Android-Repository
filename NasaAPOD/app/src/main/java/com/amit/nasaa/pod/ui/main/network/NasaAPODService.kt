package com.amit.nasaa.pod.ui.main.network

import com.amit.nasaa.pod.ui.main.model.APODResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.nasa.gov/planetary/"
private const val PATH_APOD = "apod"
private const val QUERY_API_KEY = "api_key"
const val KEY = "qlZzNvf5eQwdpCxYPIKfftH9OF7mPylHcTE3DrUf"

private val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL).build()

interface NasaAPODService {
    @GET(PATH_APOD)
    suspend fun getAPOD(@Query(QUERY_API_KEY) key:String): Response<APODResponse>
}

object APODApi {
    val retrofitService : NasaAPODService by lazy {
        retrofit.create(NasaAPODService::class.java) }
}