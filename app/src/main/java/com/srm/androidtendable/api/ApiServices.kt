package com.srm.androidtendable.api

import com.srm.androidtendable.model.InspectionResponse
import com.srm.androidtendable.model.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiServices {

    @POST("/api/register")
    suspend fun register(@Body loginRequest: LoginRequest) : Response<Unit>

    @POST("/api/login")
    suspend fun login(@Body loginRequest: LoginRequest) : Response<Unit>

    @GET("api/inspections/start")
    suspend fun getInspections() : Response<InspectionResponse>

    @POST("/api/inspections/submit")
    suspend fun submit(@Body inspectionResponse: InspectionResponse) : Response<Unit>

}