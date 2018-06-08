package br.com.leandro.moviedbservice

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.threeten.bp.Clock
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

internal const val API_KEY_NAME = "api_key"
internal const val API_KEY_VALUE = "0d0ed06f1335ea981bf483412b8a9cc5"
internal const val API_LANGUAGE_NAME = "language"
internal const val API_LANGUAGE_VALUE = "pt-BR"

object MoviedbModule {
    lateinit var retrofit: Retrofit private set

    fun setRetrofit(moviedbEndpoint: MoviedbApiEndpoint, logLevel: LoggingInterceptor.Level = LoggingInterceptor.Level.FULL) {
        val builder = OkHttpClient.Builder()
        val loggingInterceptor = LoggingInterceptor(Clock.systemDefaultZone(), logLevel)
        builder.addInterceptor(loggingInterceptor)
        builder.addInterceptor { chain ->
            val original = chain.request()
            val url = original.url().newBuilder()
                    .addQueryParameter(API_KEY_NAME, API_KEY_VALUE)
                    .addQueryParameter(API_LANGUAGE_NAME, API_LANGUAGE_VALUE)
                    .build()

            val requestBuilder = original.newBuilder().url(url)
            requestBuilder.header("Content-Type", "application/json")
            val request = requestBuilder.build()
            chain.proceed(request)
        }
        val okClient = builder.build()
        retrofit = Retrofit.Builder()
                .baseUrl(moviedbEndpoint.url)
                .client(okClient)
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    fun gsonBuilder(): Gson {
        return GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .setPrettyPrinting()
                .create()
    }
}