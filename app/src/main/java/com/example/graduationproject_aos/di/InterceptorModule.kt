package com.example.graduationproject_aos.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import android.content.Context
import com.example.graduationproject_aos.data.interceptor.AuthInterceptor
import com.example.graduationproject_aos.data.service.AuthService
import com.example.graduationproject_aos.domain.repository.DataStoreRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Provider
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InterceptorModule {

    @Provides
    @Singleton
    fun provideAuthInterceptor(
        @ApplicationContext context: Context,
        json: Json,
        dataStoreRepository: DataStoreRepository,
        authService: Provider<AuthService>
    ): AuthInterceptor {
        return AuthInterceptor(context, json, dataStoreRepository, authService)
    }
}