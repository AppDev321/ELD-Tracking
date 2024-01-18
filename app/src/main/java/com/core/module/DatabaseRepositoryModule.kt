package com.core.module


import com.core.database.repo.dictionary.DictionaryRepo
import com.core.database.repo.dictionary.DictionaryRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DatabaseRepositoryModule {
    @Binds
    abstract fun bindDictionaryDbRepository(dictionaryRepoImpl: DictionaryRepoImpl): DictionaryRepo
}
