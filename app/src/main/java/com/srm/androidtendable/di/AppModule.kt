package com.srm.androidtendable.di

import com.srm.androidtendable.repository.ApiRepository
import com.srm.androidtendable.viewmodel.HomeViewModel
import com.srm.androidtendable.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    factory {
        ApiRepository(get())
    }
    viewModel {
        LoginViewModel(get())
    }
    viewModel {
        HomeViewModel(get())
    }
}