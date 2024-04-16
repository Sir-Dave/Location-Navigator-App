package com.sirdave.campusnavigator.data

import android.content.Context
import com.sirdave.campusnavigator.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.osmdroid.bonuspack.routing.OSRMRoadManager
import org.osmdroid.bonuspack.routing.Road
import org.osmdroid.util.GeoPoint
import javax.inject.Inject

class OSMRepository @Inject constructor(private val context: Context) {

    suspend fun getRoad(
        startPoint: GeoPoint,
        endPoint: GeoPoint,
        commuteMode: String = OSRMRoadManager.MEAN_BY_CAR
    ): Resource<Road>{

        return withContext(Dispatchers.IO) {
            val roadManager = OSRMRoadManager(context, "MY_USER_AGENT")
            roadManager.setMean(commuteMode)
            val waypoints = arrayListOf(startPoint, endPoint)
            val road = roadManager.getRoad(waypoints)

            when (road.mStatus){
                Road.STATUS_INVALID, Road.STATUS_TECHNICAL_ISSUE -> {
                    Resource.Error(message = "There was an error retrieving road data", data = null)
                }

                Road.STATUS_OK -> {
                    Resource.Success(data = road)
                }
                else -> Resource.Loading(isLoading = true)
            }
        }
    }
}