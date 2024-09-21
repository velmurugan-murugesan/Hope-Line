package com.teama.hopeline.data.model

data class Incident(
    val id: Long = 0,
    val title: String = "",
    val description: String = "",
    val location: String = "",
    val noOfVolunteer: String = "",
    val isHub: Boolean
)