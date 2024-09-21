package com.teama.hopeline.data

import com.teama.hopeline.data.model.Incident
import retrofit2.http.GET

interface ApiService {
    @GET("api/incidents")
    suspend fun getAllIncidents(): List<Incident>
}