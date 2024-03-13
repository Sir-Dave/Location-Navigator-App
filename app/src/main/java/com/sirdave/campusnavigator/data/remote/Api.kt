package com.sirdave.campusnavigator.data.remote

import com.sirdave.campusnavigator.data.remote.dto.response.PlaceDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface Api {

    companion object{
        const val BASE_URL = "https://location-navigator-backend-055a9239a7e2.herokuapp.com/"
    }

    @GET("api/v1/places")
    suspend fun getPlacesByName(
        @Query("name") name: String
    ): Response<List<PlaceDto>>

    @GET("api/v1/places")
    suspend fun getPlacesByType(
        @Query("email") type: String
    ): Response<List<PlaceDto>>

    @GET("api/v1/places")
    suspend fun getAllPlaces(): Response<List<PlaceDto>>

    @GET("api/v1/places/{id}")
    suspend fun findPlaceById(
        @Path("id") id: Long
    ): Response<PlaceDto>


}