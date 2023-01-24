package com.example.simplebasketapp.di

import com.example.simplebasketapp.data.BasketRepositoryImp
import com.example.simplebasketapp.domain.BasketRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindBasketRepository(basketRepositoryImp: BasketRepositoryImp) : BasketRepository
}