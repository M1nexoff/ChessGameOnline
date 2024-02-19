package uz.b7.chessonline.di

import android.content.SharedPreferences
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.b7.chessonline.data.sourse.MyShar
import uz.b7.chessonline.data.sourse.MySharImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface MySharedModule {
    @[Binds Singleton]
    fun providePref(impl: MySharImpl): MyShar

}