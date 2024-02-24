package uz.b7.chessonline.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.b7.chessonline.domain.GameRepository
import uz.b7.chessonline.domain.LogInRepository
import uz.b7.chessonline.domain.SearchRepository
import uz.b7.chessonline.domain.SingUpRepository
import uz.b7.chessonline.domain.impl.GameRepositoryImpl
import uz.b7.chessonline.domain.impl.LogInRepositoryImpl
import uz.b7.chessonline.domain.impl.SearchRepositoryImpl
import uz.b7.chessonline.domain.impl.SingUpRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @[Binds Singleton]
    fun getContactRepository(impl: GameRepositoryImpl) : GameRepository
    @[Binds Singleton]
    fun getSearchRepository(impl: SearchRepositoryImpl) : SearchRepository
    @[Binds Singleton]
    fun getLogInRepository(impl: LogInRepositoryImpl) : LogInRepository

    @[Binds Singleton]
    fun getSingUpRepository(impl: SingUpRepositoryImpl) : SingUpRepository
}
