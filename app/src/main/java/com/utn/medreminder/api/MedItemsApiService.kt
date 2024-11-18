package com.utn.medreminder.api
import com.utn.medreminder.model.MedItem
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface MedItemApiService {
    @GET("api/meditems")
    suspend fun getMedItems(): List<MedItem>

    @POST("api/meditems")
    suspend fun createMedItem(@Body medItem: MedItem): MedItem

    @GET("api/meditems/{id}")
    suspend fun getMedItem(@Path("id") id: Long): MedItem

    @PUT("api/meditems/{id}")
    suspend fun updateMedItem(@Path("id") id: Long, @Body medItem: MedItem): Response<Unit>

    @DELETE("api/meditems/{id}")
    suspend fun deleteMedItem(@Path("id") id: Long): Response<Unit>
}

object RetrofitInstance {
    private const val BASE_URL = "http://10.0.2.2:8085/" // Dirección para localhost en Android Emulator

    val api: MedItemApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MedItemApiService::class.java)
    }
}