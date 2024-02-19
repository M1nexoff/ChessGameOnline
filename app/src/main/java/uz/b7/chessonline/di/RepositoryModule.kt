package com.sudo_pacman.uzumseller.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.sudo_pacman.uzumseller.domain.AppRepository
import com.sudo_pacman.uzumseller.domain.AppRepositoryImpl
@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun getContactRepository(impl: AppRepositoryImpl) : AppRepository
}
