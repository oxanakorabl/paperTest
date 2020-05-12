package com.paperapp.dagger.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.paperapp.BuildConfig
import com.paperapp.configuration.ConfigurationOfBackend
import com.paperapp.data.Example
import com.paperapp.network.ExampleDeserializer
import com.paperapp.network.api.AuthApi
import com.paperapp.network.dto.*
import com.paperapp.network.interceptors.SliceLinesInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.*
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun provideAuthApi(gson: Gson, okHttpClient: OkHttpClient): AuthApi {
        return getRetrofit(gson = gson, okHttpClient = okHttpClient)
            .create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .registerTypeAdapter(Example::class.java, ExampleDeserializer())
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z")
            .create()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(gson: Gson): OkHttpClient {
        val builder = OkHttpClient.Builder()

        builder
            .addInterceptor(getResponseInterceptor(gson))
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(SliceLinesInterceptor.getInterceptor())
        }

        return builder.build()
    }

    private fun getRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ConfigurationOfBackend.getCurrent().apiUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    private fun getResponseInterceptor(gson: Gson): Interceptor {
        return object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val request = chain.request()
                val response = chain.proceed(request)

                val responseBodyString = response.body?.string()

                val baseResponse = when (response.code) {
                    in 200..299 -> {
                        BaseResponse.ofSuccess(
                            success = BaseResponseSuccess(
                                data = responseBodyString?.let { JsonParser.parseString(it) }
                            )
                        )
                    }
                    BaseResponseCode.UNMODIFIED.code -> {
                        BaseResponse.ofUnmodified(
                            unmodified = BaseResponseUnmodified(
                                responseMessage = response.message
                            )
                        )
                    }
                    else -> {
                        BaseResponse.ofFailure(
                            failure = BaseResponseFailure(
                                responseCode = response.code,
                                responseMessage = response.message
                            )
                        )
                    }
                }

                val baseResponseJson = gson.toJson(baseResponse)

                val contentType: MediaType? = response.body!!.contentType()

                val body: ResponseBody = baseResponseJson.toResponseBody(contentType)

                return response
                    .newBuilder()
                    .code(200)
                    .body(body)
                    .build()
            }
        }
    }
}