package com.srm.androidtendable.repository

import com.srm.androidtendable.api.ApiServices
import com.srm.androidtendable.model.InspectionResponse
import com.srm.androidtendable.model.LoginRequest
import com.srm.androidtendable.utils.DataStatus
import com.srm.androidtendable.utils.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class ApiRepository(
    private val apiServices: ApiServices,
) {
    suspend fun register() = flow {
        emit(UIState.Loading)
        val result = apiServices.register(LoginRequest())
        when (result.code()) {
            200 -> emit(UIState.Success(result.code()))
            400 -> emit(UIState.Error)
            401 -> emit(UIState.AlreadyExists)
        }
    }.flowOn(Dispatchers.IO)

    suspend fun login() = flow {
        emit(UIState.Loading)
        try {
            val result = apiServices.login(LoginRequest())
            when (result.code()) {
                200 -> emit(UIState.Success(result.code()))
                400, 401 -> emit(UIState.Error)
            }
        } catch (ex: Exception) {
            emit(UIState.Error)
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getInspections() = flow {
        emit(DataStatus.loading())
        val result = apiServices.getInspections()
        when (result.code()) {
            200 -> emit(DataStatus.success(result.body()))
            400,401 -> emit(DataStatus.error(result.message()))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun submitInspections(inspections: InspectionResponse) = flow {
        emit(UIState.Loading)
        val result = apiServices.submit(inspections)
        when (result.code()) {
            200 -> emit(UIState.Success(result.code()))
            else -> emit(UIState.Error)
        }
    }.flowOn(Dispatchers.IO)
}