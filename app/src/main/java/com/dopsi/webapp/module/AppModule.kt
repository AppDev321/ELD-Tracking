package com.dopsi.webapp.module

import android.content.Context
import com.dopsi.webapp.utils.DeviceInfoUtilClass
import com.dopsi.webapp.utils.PreferenceManager
import com.dopsi.webapp.utils.ResourceHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePreferenceManager(@ApplicationContext appContext: Context) =
        PreferenceManager(appContext)


    @Provides
    @Singleton
    fun provideResourceHelper(@ApplicationContext appContext: Context) =
        ResourceHelper(appContext)

    @Provides
    @Singleton
    fun provideDeviceInfoUtil(@ApplicationContext appContext: Context) =
        DeviceInfoUtilClass(appContext)
}