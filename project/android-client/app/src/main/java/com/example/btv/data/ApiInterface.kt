package com.example.btv.data

import com.example.btv.models.Channel
import com.example.btv.models.Program
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET("/api/channels")
    suspend fun getChannels(): Response<List<Channel>>

    @GET("/api/channels/{channelNumber}/epgs")
    suspend fun getProgramsByChannel(@Path("channelNumber") channelNumber: Int): Response<List<Program>>
}