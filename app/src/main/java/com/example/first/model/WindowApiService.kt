package com.example.first.model

import WindowDto
import retrofit2.Call
import retrofit2.http.*


interface WindowApiService {
    @GET("windows")
    fun findAll(): Call<List<WindowDto>>

    @GET("windows/{id}")
    fun findById(@Path("id") id: Long): Call<WindowDto>

}