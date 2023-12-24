package com.dopsi.webapp.module

import android.content.Context
import com.dopsi.webapp.model.AccountDataFactory
import com.dopsi.webapp.model.FragmentDTODataFactory
import com.dopsi.webapp.model.VehicleDataFactory
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


    @Provides
    @Singleton
    fun provideAccountDataFactory(
        @ApplicationContext appContext: Context,

        ) = AccountDataFactory(appContext)


    @Provides
    @Singleton
    fun provideVehicleDataFactory(
        @ApplicationContext appContext: Context,

        ) = VehicleDataFactory(appContext)

}