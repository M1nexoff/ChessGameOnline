package uz.b7.chessonline.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.b7.chessonline.domain.GameRepository
import uz.b7.chessonline.domain.impl.GameRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun getContactRepository(impl: GameRepositoryImpl) : GameRepository
}
