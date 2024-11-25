package com.utn.medreminder.api

import com.utn.medreminder.model.MedAlarm
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.*

interface AlarmMedApiService {

    @GET("api/alarm-med-items/next-alarm-by-medId/{id}")
    suspend fun getNextAlarmByMedId(@Path("id") medId: Long): MedAlarm

    @PUT("api/alarm-med-items/toFinishStatus/{id}")
    suspend fun finishAlarmStatus(@Path("id") alarmId: Long): Response<Unit>

    @PUT("api/alarm-med-items/toReadyStatus/{id}")
    suspend fun readyAlarmStatus(@Path("id") alarmId: Long): Response<Unit>

}

object RetrofitInstanceAlarmMed {
    private const val BASE_URL = "http://10.0.2.2:8085/" // Dirección para localhost en Android Emulator

    val api: AlarmMedApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AlarmMedApiService::class.java)
    }
}
