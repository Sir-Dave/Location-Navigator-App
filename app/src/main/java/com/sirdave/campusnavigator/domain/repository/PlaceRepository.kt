package com.sirdave.campusnavigator.domain.repository

import com.sirdave.campusnavigator.domain.model.Place
import com.sirdave.campusnavigator.util.Resource
import kotlinx.coroutines.flow.Flow

interface PlaceRepository {

    suspend fun searchPlacesByName(
        name: String,
        fetchFromRemote: Boolean
    ): Flow<Resource<List<Place>>>

    suspend fun searchPlacesByCategory(
        category: String,
        fetchFromRemote: Boolean
    ): Flow<Resource<List<Place>>>

    suspend fun findPlaceById(
        id: Long,
        fetchFromRemote: Boolean
    ): Flow<Resource<Place>>


    suspend fun findAllPlaces(fetchFromRemote: Boolean): Flow<Resource<List<Place>>>
}