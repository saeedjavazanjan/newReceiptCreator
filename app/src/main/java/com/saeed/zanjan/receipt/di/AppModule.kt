package com.saeed.zanjan.receipt.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.room.Room
import com.google.gson.GsonBuilder
import com.saeed.zanjan.receipt.BaseApplication
import com.saeed.zanjan.receipt.cash.ReceiptDao
import com.saeed.zanjan.receipt.cash.database.AppDatabase
import com.saeed.zanjan.receipt.cash.model.ConfectioneryEntityMapper
import com.saeed.zanjan.receipt.cash.model.EntitiesGeneralMapper
import com.saeed.zanjan.receipt.cash.model.JewelryEntityMapper
import com.saeed.zanjan.receipt.cash.model.LaundryEntityMapper
import com.saeed.zanjan.receipt.cash.model.OtherJobsEntityMapper
import com.saeed.zanjan.receipt.cash.model.PhotographyEntityMapper
import com.saeed.zanjan.receipt.cash.model.RepairsEntityMapper
import com.saeed.zanjan.receipt.cash.model.TailoringEntityMapper
import com.saeed.zanjan.receipt.interactor.ListOfReceipts
import com.saeed.zanjan.receipt.interactor.SaveReceiptInDatabase
import com.saeed.zanjan.receipt.interactor.SendSms
import com.saeed.zanjan.receipt.interactor.UserRegistration
import com.saeed.zanjan.receipt.network.RetrofitService
import com.saeed.zanjan.receipt.network.model.OtpDataDtoMapper
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
            //.baseUrl("http://192.168.1.167:5047/")
            .baseUrl("http://10.0.2.2:5047/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(client)
            .build()

            .create(RetrofitService::class.java)
    }
    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
       // return context.getSharedPreferences("YOUR_PREFERENCE_NAME", Context.MODE_PRIVATE)
       return PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Singleton
    @Provides
    fun provideSharedPreferencesEditor(sharedPreferences: SharedPreferences):SharedPreferences.Editor{
        return sharedPreferences.edit()
    }

    @Singleton
    @Provides
    fun provideRegistrationInfoDtoMapper():RegistrationInfoDtoMapper{
        return RegistrationInfoDtoMapper()
    }

    @Singleton
    @Provides
    fun provideOtpDataDtoMapper():OtpDataDtoMapper{
        return OtpDataDtoMapper()
    }

    @Singleton
    @Provides
    fun provideUserRegistration(
        retrofitService: RetrofitService,
        sharedPreferences: SharedPreferences,
        editor: SharedPreferences.Editor,
        registrationDtoMapper: RegistrationInfoDtoMapper,
        otpDataDtoMapper: OtpDataDtoMapper
    ):UserRegistration{
        return UserRegistration(
            retrofitService=retrofitService,
            registrationDtoMapper=registrationDtoMapper,
            sharedPreferences=sharedPreferences,
            editor=editor,
            otpDataDtoMapper = otpDataDtoMapper
        )

    }
    @Singleton
    @Provides
    fun provideDb(@ApplicationContext context: Context): AppDatabase {
        return Room
            .databaseBuilder(context, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .addMigrations(AppDatabase.MIGRATION_1_2)
            .build()
    }

    @Singleton
    @Provides
    fun provideRecipeDao(db: AppDatabase): ReceiptDao{
        return db.receiptDao()
    }
/*

    @Singleton
    @Provides
    fun provideConfectioneryMapper(): ConfectioneryEntityMapper {
        return ConfectioneryEntityMapper()
    }
 @Singleton
    @Provides
    fun provideJewelryMapper(): JewelryEntityMapper {
        return JewelryEntityMapper()
    }
 @Singleton
    @Provides
    fun provideLaundryMapper(): LaundryEntityMapper {
        return LaundryEntityMapper()
    }
 @Singleton
    @Provides
    fun provideOtherJobsMapper(): OtherJobsEntityMapper {
        return OtherJobsEntityMapper()
    }
 @Singleton
    @Provides
    fun providePhotoMapper(): PhotographyEntityMapper {
        return PhotographyEntityMapper()
    }
 @Singleton
    @Provides
    fun provideRepairsMapper(): RepairsEntityMapper {
        return RepairsEntityMapper()
    }
 @Singleton
    @Provides
    fun provideTailoringMapper(): TailoringEntityMapper {
        return TailoringEntityMapper()
    }
*/


    @Singleton
    @Provides
    fun provideGeneralMapper(): EntitiesGeneralMapper {
        return EntitiesGeneralMapper()
    }

    @Singleton
    @Provides
    fun provideReceiptSaver(
        receiptDao: ReceiptDao,
        generalMapper: EntitiesGeneralMapper
    ):SaveReceiptInDatabase{
        return SaveReceiptInDatabase(
           receiptDao=receiptDao,
            generalMapper=generalMapper

        )
    }

    @Singleton
    @Provides
    fun provideSmsSender(
        sharedPreferences: SharedPreferences
    ):SendSms{
        return SendSms(
            sharedPreferences=sharedPreferences
        )
    }
    @Singleton
    @Provides
    fun provideListOfReceipts(
        receiptDao: ReceiptDao,
        generalMapper: EntitiesGeneralMapper
    ): ListOfReceipts {
        return ListOfReceipts(
            receiptDao=receiptDao,
            generalMapper=generalMapper
        )
    }
}