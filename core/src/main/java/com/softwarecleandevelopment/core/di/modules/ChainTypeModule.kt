package com.softwarecleandevelopment.core.di.modules

import com.softwarecleandevelopment.core.crypto.ChainType
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object ChainTypeModule {
    @Provides
    fun provideMyEnum(): Array<ChainType> {
        return ChainType.entries.toTypedArray()
    }
}