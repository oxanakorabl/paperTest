package com.paper.paperapp.dagger.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.paper.paperapp.BuildConfig
import com.paper.paperapp.api.Api
import com.paper.paperapp.api.CurlLoggingInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSession
import javax.net.ssl.X509TrustManager


@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideApi(gson: Gson, okHttpClient: OkHttpClient): Api {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(Api::class.java)
    }


    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            // .registerTypeAdapter(Coord::class.java, CoordDeserializer())
            // .registerTypeAdapter(Product::class.java, ProductDeserializer())
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z")
            .create()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        builder
            // .acceptNotTrustedCertificates()
            .addInterceptor(getInterceptor())
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {

            val logging = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    sliceLongResponseIntoLines(message)
                }
            })
            logging.level = HttpLoggingInterceptor.Level.BODY

            builder.addNetworkInterceptor(logging)
            builder.addNetworkInterceptor(CurlLoggingInterceptor())
        }
        return builder.build()
    }
}


fun sliceLongResponseIntoLines(str: String) {
    when {
        str.contains("�") -> {
            // do nothing
        }
        str.length > 3000 -> {
            Timber.tag("OkHttp").i(str.substring(0, 3000))
            sliceLongResponseIntoLines(str.substring(3000))
        }
        else -> Timber.tag("OkHttp").i(str)
    }

}

fun getInterceptor(): Interceptor {

    return Interceptor { chain ->
        val original = chain.request()

        val requestBuilder = original.newBuilder()
            .header("User-Agent", "android")
            .header("Authorization", BuildConfig.API_KEY)
        // .header("", "")


        val request = requestBuilder.build()
        chain.proceed(request)
    }

}


private fun OkHttpClient.Builder.acceptNotTrustedCertificates(): OkHttpClient.Builder {

    // Create a trust manager that does not validate certificate chains
    val trustManager = object : X509TrustManager {
        override fun getAcceptedIssuers(): Array<X509Certificate>? {
            return arrayOf()
        }

        @Throws(CertificateException::class)
        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
        }

        @Throws(CertificateException::class)
        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
        }
    }

    // Install the all-trusting trust manager
    val sslContext = SSLContext.getInstance("SSL")
    sslContext.init(null, arrayOf(trustManager), java.security.SecureRandom())
    // Create an ssl socket factory with our all-trusting manager
    val sslSocketFactory = sslContext.socketFactory
    sslSocketFactory(sslSocketFactory, trustManager)
    hostnameVerifier(HostnameVerifier { _, _ -> true })

    return this
}
