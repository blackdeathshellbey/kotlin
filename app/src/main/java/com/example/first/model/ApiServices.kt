package com.example.first.model

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

const val API_USERNAME = "user"
const val API_PASSWORD = "password"

class ApiServices {
    val userInfo = Credentials.basic(API_USERNAME, API_PASSWORD)
    val interceptor = Interceptor { chain ->
        val request = chain.request()
        val authenticatedRequest = request.newBuilder()
            .header("Authorization", userInfo).build()
        chain.proceed(authenticatedRequest)
    }
    val logging = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    val userAccount = OkHttpClient.Builder().addInterceptor(interceptor).addInterceptor(logging)
        .build()

    val windowsApiService : WindowApiService by lazy {
        val client = OkHttpClient.Builder()
            .addInterceptor(BasicAuthInterceptor(API_USERNAME, API_PASSWORD))
            .build()

        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .baseUrl("http://faircorp-client-for-android.cleverapps.io:80/")
            .build()
            .create(WindowApiService::class.java)

    }


    class BasicAuthInterceptor(val username: String, val password: String): Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain
                .request()
                .newBuilder()
                .header("Authorization", Credentials.basic(username, password))
                .build()
            return chain.proceed(request)
        }
    }
}