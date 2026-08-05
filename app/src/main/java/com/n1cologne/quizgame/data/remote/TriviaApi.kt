package com.n1cologne.quizgame.data.remote

import com.n1cologne.quizgame.data.remote.dto.TriviaResponseDto
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://opentdb.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val loggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(loggingInterceptor)
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .addConverterFactory(
        MoshiConverterFactory.create(moshi)
    )
    .build()

interface TriviaApiService {

    @GET("api.php")
    suspend fun getQuestions(
        @Query("amount")
        amount: Int,

        @Query("difficulty")
        difficulty: String? = null,

        @Query("type")
        type: String = "multiple"
    ): TriviaResponseDto
}

object TriviaApi {

    val retrofitService: TriviaApiService by lazy {
        retrofit.create(TriviaApiService::class.java)
    }
}