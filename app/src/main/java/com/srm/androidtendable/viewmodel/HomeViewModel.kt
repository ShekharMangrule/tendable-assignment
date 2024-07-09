package com.srm.androidtendable.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srm.androidtendable.model.InspectionResponse
import com.srm.androidtendable.repository.ApiRepository
import com.srm.androidtendable.utils.DataStatus
import com.srm.androidtendable.utils.UIState
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: ApiRepository) : ViewModel() {

    private val _inspectionsResult = MutableLiveData<DataStatus<InspectionResponse>>()
    val inspectionsResult: LiveData<DataStatus<InspectionResponse>>
        get() = _inspectionsResult

    private val _submitResult = MutableLiveData<UIState>()
    val submitResult: LiveData<UIState>
        get() = _submitResult

    fun getInspections() = viewModelScope.launch {
        repository.getInspections().collect {
            _inspectionsResult.value = it
        }
    }

    fun submitInspections(inspections: InspectionResponse) = viewModelScope.launch {
        repository.submitInspections(inspections).collect {
            _submitResult.value = it
        }
    }

}