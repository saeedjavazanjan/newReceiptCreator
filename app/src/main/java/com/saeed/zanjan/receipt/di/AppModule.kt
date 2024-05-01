package com.saeed.zanjan.receipt.di

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import com.saeed.zanjan.receipt.BaseApplication
import com.saeed.zanjan.receipt.interactor.UserRegistration
import com.saeed.zanjan.receipt.network.RetrofitService
import com.saeed.zanjan.receipt.network.model.RegistrationInfoDtoMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication {
        return app as BaseApplication
    }
    @Provides
    @Singleton
    fun provideInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }
    @Provides
    @Singleton
    fun provideOkHttpClient(
        interceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(interceptor).build()
    }

    @Singleton
    @Provides
    fun provideRetrofitService(
        client: OkHttpClient
    ): RetrofitService {
        return Retrofit.Builder()
            //.baseUrl("https://dev-xf7awpzkvndkoch.api.raw-labs.com/")
            //  .baseUrl("https://food2fork.ca/api/recipe/")
            .baseUrl("http://10.0.2.2:5198/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(client)
            .build()

            .create(RetrofitService::class.java)
    }
    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("YOUR_PREFERENCE_NAME", Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideRegistrationInfoDtoMapper():RegistrationInfoDtoMapper{
        return RegistrationInfoDtoMapper()
    }

    @Singleton
    @Provides
    fun provideUserRegistration(
        retrofitService: RetrofitService,
        sharedPreferences: SharedPreferences,
        dtoMapper: RegistrationInfoDtoMapper
    ):UserRegistration{
        return UserRegistration(
            retrofitService=retrofitService,
            dtoMapper=dtoMapper,
            sharedPreferences=sharedPreferences
        )

    }
}