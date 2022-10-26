package mx.com.satoritech.satoritickets.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mx.com.satoritech.web.APIConstants
import mx.com.satoritech.web.ApiService
import mx.com.satoritech.web.Token
import mx.com.satoritech.web.serializers.BooleanDeserializer
import mx.com.satoritech.web.serializers.BooleanSerializer
import mx.com.satoritech.web.serializers.DateDeserializaer
import mx.com.satoritech.web.serializers.DateSerializer
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {
    @Provides
    fun provideRetrofit(): ApiService {
        if(Token.retrofitInstance == null){
            Token.retrofitInstance = Retrofit.Builder()
                .baseUrl(APIConstants.getServerPath())
                .addConverterFactory(GsonConverterFactory.create(gson()))
                .client(provideHttpClient())
                .build()
                .create(ApiService::class.java)
        }
        return Token.retrofitInstance!!
    }

    private fun loggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor

    }

    private fun provideHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor())
            .addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer " + Token.token)
                    .build()
                chain.proceed(newRequest)
            }
            .build()
    }

    private fun gson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(Boolean::class.java, BooleanSerializer())
            .registerTypeAdapter(Boolean::class.java, BooleanDeserializer())
            .registerTypeAdapter(Boolean::class.javaPrimitiveType, BooleanSerializer())
            .registerTypeAdapter(Boolean::class.javaPrimitiveType, BooleanDeserializer())
            .registerTypeAdapter(Date::class.java, DateSerializer())
            .registerTypeAdapter(Date::class.java, DateDeserializaer())
            .create()
    }

}