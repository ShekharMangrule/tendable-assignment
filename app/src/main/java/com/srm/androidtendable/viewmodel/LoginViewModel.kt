package com.srm.androidtendable.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srm.androidtendable.repository.ApiRepository
import com.srm.androidtendable.utils.UIState
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: ApiRepository) : ViewModel() {

    private val _registerUserResult = MutableLiveData<UIState>()
    val registerUserResult: LiveData<UIState>
        get() = _registerUserResult

    private val _loginUserResult = MutableLiveData<UIState>()
    val loginUserResult: LiveData<UIState>
        get() = _loginUserResult

    fun register() = viewModelScope.launch {
        repository.register().collect {
            _registerUserResult.value = it
        }
    }

    fun login() = viewModelScope.launch {
        repository.login().collect {
            _loginUserResult.value = it
        }
    }

}