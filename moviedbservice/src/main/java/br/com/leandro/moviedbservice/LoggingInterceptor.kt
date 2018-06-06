package br.com.leandro.moviedbservice

import android.util.Log
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer
import org.threeten.bp.Clock
import java.io.IOException

/**
 * Verbose logging of network calls, which includes path, headers, and times.
 */
class LoggingInterceptor(private val clock: Clock, private val logLevel: Level) : Interceptor {

    enum class Level {
        NONE,
        BASIC,
        HEADERS,
        FULL
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val startMs = clock.millis()
        if (logLevel.ordinal >= Level.BASIC.ordinal) {
            Log.v(TAG, String.format("---> %s %s", request.method(), request.url()))
        }
        if (logLevel.ordinal >= Level.HEADERS.ordinal) {
            Log.v(TAG, prettyHeaders(request.headers()))
        }
        if (logLevel.ordinal >= Level.FULL.ordinal && request.body() != null) {
            val copy = request.newBuilder().build()
            val buffer = Buffer()
            buffer.use { buffer ->
                copy.body()!!.writeTo(buffer)
                val bodyStr = buffer.readUtf8()
                Log.v(TAG, bodyStr)
            }
        }

        var response = chain.proceed(request)

        val tookMs = clock.millis() - startMs
        if (logLevel.ordinal >= Level.BASIC.ordinal) {
            Log.v(TAG, String.format("<--- (%s) for %s %s in %sms", response.code(), request.method(),
                    response.request().url(), tookMs))
        }
        if (logLevel.ordinal >= Level.HEADERS.ordinal) {
            Log.v(TAG, prettyHeaders(response.headers()))
        }
        if (logLevel.ordinal >= Level.FULL.ordinal && response.body() != null) {
            val responseBody = response.body()
            val responseBodyString = responseBody!!.string()
            Log.v(TAG, responseBodyString)

            // response body was consumed, replace it with a copy
            val bodyCopy = ResponseBody.create(responseBody.contentType(), responseBodyString)
            response = response.newBuilder().body(bodyCopy).build()
        }

        return response
    }

    private fun prettyHeaders(headers: Headers): String {
        if (headers.size() == 0) return ""

        val builder = StringBuilder()
        builder.append("  Headers:")

        for (i in 0 until headers.size()) {
            builder.append("\n    ").append(headers.name(i)).append(": ").append(headers.value(i))
        }

        return builder.toString()
    }

    companion object {
        private val TAG = "OkHttp"
    }
}