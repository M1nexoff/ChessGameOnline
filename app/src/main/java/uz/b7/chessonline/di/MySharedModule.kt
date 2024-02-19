package com.sudo_pacman.uzumseller.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MySharedModule {
    @[Provides Singleton]
    fun providePref(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("AppPref", Context.MODE_PRIVATE)
}