package com.elewamathtutoring.network


import com.elewamathtutoring.Util.constant.Constants
import com.elewamathtutoring.Util.helper.extensions.getPrefrence
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.ConnectionSpec
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit


object ServiceGenerator {
    private val httpClient = OkHttpClient.Builder()
        .readTimeout((5 * 60).toLong(), TimeUnit.SECONDS)
        .connectTimeout((5 * 60).toLong(), TimeUnit.SECONDS)
        .writeTimeout((5 * 60).toLong(), TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .addInterceptor(provideHeaderInterceptor())
        .connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.CLEARTEXT)).build()

    val gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
        .create()

    private val builder = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())


    @JvmStatic
    fun <S> createService(serviceClass: Class<S>): S {
        val retrofit = getRetrofit()
        return retrofit.create(serviceClass)
    }

    @JvmStatic
    fun getRetrofit(): Retrofit {

        return builder.client(httpClient)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setPrettyPrinting().create()
                )
            )
            .build()
    }

    private fun provideHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request: Request
            if (getPrefrence(Constants.AUTH_KEY, "") != null && !getPrefrence(Constants.AUTH_KEY, "").isEmpty()) {
                request = chain.request().newBuilder()
                    .header(Constants.SECURITY_KEY, Constants.SECURITY_KEY_VALUE)
                    .header("Authorization","Bearer "+ getPrefrence(Constants.AUTH_KEY, ""))
                    .header("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                    .build()
            }
            else {
                request = chain.request().newBuilder()
                    .header(Constants.SECURITY_KEY, Constants.SECURITY_KEY_VALUE)
                    .addHeader("Content-Type", "application/json")
                    .build()
            }
            chain.proceed(request)
        }
    }
}