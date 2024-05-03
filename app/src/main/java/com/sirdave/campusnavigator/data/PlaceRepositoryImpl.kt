package com.sirdave.campusnavigator.data

import android.content.Context
import android.util.Log
import com.sirdave.campusnavigator.data.local.NavigatorDatabase
import com.sirdave.campusnavigator.data.mapper.toPlace
import com.sirdave.campusnavigator.data.mapper.toPlaceEntity
import com.sirdave.campusnavigator.data.remote.Api
import com.sirdave.campusnavigator.domain.model.Place
import com.sirdave.campusnavigator.domain.repository.PlaceRepository
import com.sirdave.campusnavigator.util.Resource
import com.sirdave.campusnavigator.util.apiRequestFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlaceRepositoryImpl @Inject constructor(
    private val context: Context,
    private val api: Api,
    db: NavigatorDatabase
): PlaceRepository {

    companion object{
        const val TAG = "PlaceRepository"
    }

    private val dao = db.dao
    override suspend fun searchPlacesByName(
        name: String,
        fetchFromRemote: Boolean
    ): Flow<Resource<List<Place>>> {

        val localResult = dao.getPlacesByName(name)
        val shouldLoadFromCache = localResult.isNotEmpty() && !fetchFromRemote

        if (shouldLoadFromCache) {
            Log.d(TAG, "fetching places with name $name from DB")
            return flow {
                emit(Resource.Success(data = localResult.map { it.toPlace() } ))
            }
        }
        Log.d(TAG, "fetching places with name $name from server")
        val request = apiRequestFlow(context) { api.getPlacesByName(name)}
        return request.map { dtoResource ->
            when (dtoResource){
                is Resource.Success -> {
                    val placesDto = dtoResource.data!!

                    dao.insertAllPlaces(placesDto.map { it.toPlaceEntity() })
                    Resource.Success(data = placesDto.map { it.toPlace() })
                }
                is Resource.Error -> Resource.Error(message = dtoResource.message!!, data = null)
                else -> Resource.Loading(isLoading = false)
            }
        }
    }

    override suspend fun searchPlacesByCategory(
        category: String,
        fetchFromRemote: Boolean
    ): Flow<Resource<List<Place>>> {
        val localResult = dao.getPlacesByCategory(category)
        val shouldLoadFromCache = localResult.isNotEmpty() && !fetchFromRemote

        if (shouldLoadFromCache) {
            Log.d(TAG, "fetching places with category $category from DB")
            return flow {
                emit(Resource.Success(data = localResult.map { it.toPlace() } ))
            }
        }
        Log.d(TAG, "fetching places with category $category from server")
        val request = apiRequestFlow(context) { api.getPlacesByCategory(category)}
        return request.map { dtoResource ->
            when (dtoResource){
                is Resource.Success -> {
                    val placesDto = dtoResource.data!!
                    dao.insertAllPlaces(placesDto.map { it.toPlaceEntity() })
                    Resource.Success(data = placesDto.map { it.toPlace() })
                }
                is Resource.Error -> Resource.Error(message = dtoResource.message!!, data = null)
                else -> Resource.Loading(isLoading = false)
            }
        }
    }

    override suspend fun findPlaceById(id: Long, fetchFromRemote: Boolean): Flow<Resource<Place>> {
        val localResult = dao.getOnePlace(id)
        val shouldLoadFromCache = localResult != null && !fetchFromRemote

        if (shouldLoadFromCache) {
            Log.d(TAG, "fetching place with id $id from DB")
            return flow {
                emit(Resource.Success(data = localResult!!.toPlace() ))
            }
        }
        Log.d(TAG, "fetching place with id $id from server")
        val request = apiRequestFlow(context) { api.findPlaceById(id)}
        return request.map { dtoResource ->
            when (dtoResource){
                is Resource.Success -> {
                    val placeDto = dtoResource.data!!
                    dao.insertOnePlace(placeDto.toPlaceEntity())
                    Resource.Success(data = placeDto.toPlace())
                }
                is Resource.Error -> Resource.Error(message = dtoResource.message!!, data = null)
                else -> Resource.Loading(isLoading = false)
            }
        }
    }

    override suspend fun findAllPlaces(fetchFromRemote: Boolean): Flow<Resource<List<Place>>> {
        val localResult = dao.getAllPlaces()
        val shouldLoadFromCache = localResult.isNotEmpty() && !fetchFromRemote

        if (shouldLoadFromCache) {
            Log.d(TAG, "fetching all from DB")
            return flow {
                emit(Resource.Success(data = localResult.map { it.toPlace() } ))
            }
        }
        Log.d(TAG, "fetching all from server")
        val request = apiRequestFlow(context) { api.getAllPlaces()}
        return request.map { dtoResource ->
            when (dtoResource){
                is Resource.Success -> {

                    dao.clearAllPlaces()
                    val placesDto = dtoResource.data!!

                    dao.insertAllPlaces(placesDto.map { it.toPlaceEntity() })
                    Resource.Success(data = placesDto.map { it.toPlace() })
                }
                is Resource.Error -> Resource.Error(message = dtoResource.message!!, data = null)
                else -> Resource.Loading(isLoading = false)
            }
        }
    }
}