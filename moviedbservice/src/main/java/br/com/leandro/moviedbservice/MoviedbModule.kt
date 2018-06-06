package br.com.leandro.moviedbservice

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.threeten.bp.Clock
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

//internal const val BAZAAR_API_KEY_NAME = "passkey"
//internal const val BAZAAR_API_KEY_VALUE = "cagyh5dFOaI9mzDknVONsxfKnFWWnaHyN3WsghKcM7PFY"

object MoviedbModule {
    lateinit var retrofit: Retrofit private set

    fun setRetrofit(moviedbEndpoint: MoviedbApiEndpoint, logLevel: LoggingInterceptor.Level = LoggingInterceptor.Level.FULL) {
        val builder = OkHttpClient.Builder()
        val loggingInterceptor = LoggingInterceptor(Clock.systemDefaultZone(), logLevel)
        builder.addInterceptor(loggingInterceptor)
        builder.addInterceptor { chain ->
            val original = chain.request()
            val url = original.url().newBuilder()
//                    .addQueryParameter(BAZAAR_API_KEY_NAME, BAZAAR_API_KEY_VALUE)
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