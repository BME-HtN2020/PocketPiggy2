package com.example.projectpiggy2.di.modules

import com.example.projectpiggy2.user.UserWrapper
import com.example.projectpiggy2.viewmodels.PinViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val viewModelModule = module {
    /**
     * Adds our viewmodel to our dependency tree and get the dependency it needs
     * I choose to leave the explicit type argument because it makes the code much cleaner as a
     * class definition gets bigger and requires more dependencies
     */
    viewModel { PinViewModel(get<UserWrapper>()) }
}