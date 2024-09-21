package com.teama.hopeline.data

import com.teama.hopeline.data.model.Incident
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("endpoint/{id}")
    fun getData(@Path("id") id: String): List<Incident>
}