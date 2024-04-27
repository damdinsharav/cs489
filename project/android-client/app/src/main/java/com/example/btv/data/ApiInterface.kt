package com.example.btv.data

import com.example.btv.models.Channel
import com.example.btv.models.Program
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("/api/channels")
    suspend fun getChannels(): Response<List<Channel>>

    @GET("/api/channels/{channel_id}/epgs")
    suspend fun getProgramsByChannel(): Response<List<Program>>
}