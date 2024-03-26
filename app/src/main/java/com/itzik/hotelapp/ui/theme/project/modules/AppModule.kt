package com.itzik.hotelapp.ui.theme.project.modules

import android.content.Context
import androidx.room.Room
import com.itzik.hotelapp.ui.theme.project.data.UserDao
import com.itzik.hotelapp.ui.theme.project.data.UserDatabase
import com.itzik.hotelapp.ui.theme.project.repositories.IRepo
import com.itzik.hotelapp.ui.theme.project.repositories.RepoImp
import com.itzik.hotelapp.ui.theme.project.requests.PropertyService
import com.itzik.hotelapp.ui.theme.project.requests.UserService
import com.itzik.hotelapp.ui.theme.project.utils.Constants.API_HOST_NAME
import com.itzik.hotelapp.ui.theme.project.utils.Constants.API_HOST_VALUE
import com.itzik.hotelapp.ui.theme.project.utils.Constants.API_KEY_NAME
import com.itzik.hotelapp.ui.theme.project.utils.Constants.API_KEY_VALUE
import com.itzik.hotelapp.ui.theme.project.utils.Constants.BASE_URL
import com.itzik.hotelapp.ui.theme.project.utils.Constants.USER_BASE_URL
import com.itzik.hotelapp.ui.theme.project.utils.Constants.USER_DATABASE
import com.itzik.hotelapp.ui.theme.project.utils.Converters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUserConverter(): Converters = Converters()

    @Provides
    @Singleton
    fun provideRepo(repoImpl: RepoImp): IRepo = repoImpl


    @Singleton
    @Provides
    fun provideDao(userDatabase: UserDatabase): UserDao = userDatabase.getDao()


    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext applicationContext: Context) =
        Room.databaseBuilder(applicationContext, UserDatabase::class.java, USER_DATABASE)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            //.addTypeConverter(Converters())
            .fallbackToDestructiveMigration().build()


    @Provides
    @Singleton
    @Named("Properties")
    fun provideRetrofitService(): PropertyService {
        val retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient.Builder().addInterceptor(URLInterceptor()).build()).build()
        return retrofit.create(PropertyService::class.java)
    }


    @Provides
    @Singleton
    @Named("Users")
    fun provideUserRetrofitService(): UserService {
        val retrofit =
            Retrofit.Builder().baseUrl(USER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient.Builder().build()).build()
        return retrofit.create(UserService::class.java)
    }
}

class URLInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request().newBuilder()
            .addHeader(API_KEY_NAME, API_KEY_VALUE)
            .addHeader(API_HOST_NAME, API_HOST_VALUE)
            .build()
        return chain.proceed(request)
    }
}