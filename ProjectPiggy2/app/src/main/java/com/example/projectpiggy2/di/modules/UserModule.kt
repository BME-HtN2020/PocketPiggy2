package com.example.projectpiggy2.di.modules

import com.example.projectpiggy2.user.UserInfo
import com.example.projectpiggy2.user.UserWrapper
import org.koin.dsl.module

internal val userModule = module {
    /**
     * Creates the dependency that can be injected anywhere we need it injected into the application
     * single = only create this dependency once and inject that same instance wherever it's needed
     */
    single<UserWrapper> { UserInfo() }
}
