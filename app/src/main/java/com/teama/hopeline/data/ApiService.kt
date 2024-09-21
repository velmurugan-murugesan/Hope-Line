package com.teama.hopeline.data

import com.teama.hopeline.data.model.Incident
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("api/incidents")
    suspend fun getAllIncidents(): List<Incident>

    @POST("api/incidents")
    suspend fun saveIncidents(@Body incident: Incident)
}