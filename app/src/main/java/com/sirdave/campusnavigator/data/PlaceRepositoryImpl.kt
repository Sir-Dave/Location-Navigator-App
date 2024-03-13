package com.sirdave.campusnavigator.data

import android.content.Context
import com.sirdave.campusnavigator.data.mapper.toPlace
import com.sirdave.campusnavigator.data.remote.Api
import com.sirdave.campusnavigator.domain.model.Place
import com.sirdave.campusnavigator.domain.repository.PlaceRepository
import com.sirdave.campusnavigator.util.Resource
import com.sirdave.campusnavigator.util.apiRequestFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlaceRepositoryImpl @Inject constructor(
    private val context: Context,
    private val api: Api
): PlaceRepository {
    override suspend fun searchPlacesByName(
        name: String,
        fetchFromRemote: Boolean
    ): Flow<Resource<List<Place>>> {
        val request = apiRequestFlow(context) { api.getPlacesByName(name)}
        return request.map { dtoResource ->
            when (dtoResource){
                is Resource.Success -> {
                    Resource.Success(
                        data = dtoResource.data!!.map { it.toPlace() }
                    )
                }
                is Resource.Error -> Resource.Error(message = dtoResource.message!!, data = null)
                else -> Resource.Loading(isLoading = false)
            }
        }
    }

    override suspend fun searchPlacesByType(
        type: String,
        fetchFromRemote: Boolean
    ): Flow<Resource<List<Place>>> {
        val request = apiRequestFlow(context) { api.getPlacesByType(type)}
        return request.map { dtoResource ->
            when (dtoResource){
                is Resource.Success -> {
                    Resource.Success(
                        data = dtoResource.data!!.map { it.toPlace() }
                    )
                }
                is Resource.Error -> Resource.Error(message = dtoResource.message!!, data = null)
                else -> Resource.Loading(isLoading = false)
            }
        }
    }

    override suspend fun findPlaceById(id: Long, fetchFromRemote: Boolean): Flow<Resource<Place>> {
        val request = apiRequestFlow(context) { api.findPlaceById(id)}
        return request.map { dtoResource ->
            when (dtoResource){
                is Resource.Success -> {
                    Resource.Success(
                        data = dtoResource.data!!.toPlace()
                    )
                }
                is Resource.Error -> Resource.Error(message = dtoResource.message!!, data = null)
                else -> Resource.Loading(isLoading = false)
            }
        }
    }

    override suspend fun findAllPlaces(fetchFromRemote: Boolean): Flow<Resource<List<Place>>> {
        val request = apiRequestFlow(context) { api.getAllPlaces()}
        return request.map { dtoResource ->
            when (dtoResource){
                is Resource.Success -> {
                    Resource.Success(
                        data = dtoResource.data!!.map { it.toPlace() }
                    )
                }
                is Resource.Error -> Resource.Error(message = dtoResource.message!!, data = null)
                else -> Resource.Loading(isLoading = false)
            }
        }
    }
}