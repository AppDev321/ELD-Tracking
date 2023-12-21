package com.dopsi.webapp.module

import android.content.Context
import com.dopsi.webapp.model.FragmentDTODataFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UICommonModule {
    @Provides
    @Singleton
    fun provideFragmentDTODataFactory(
        @ApplicationContext appContext: Context,

    ) = FragmentDTODataFactory(appContext)

}