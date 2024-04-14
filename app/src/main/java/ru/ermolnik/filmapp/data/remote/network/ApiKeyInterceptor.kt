package ru.ermolnik.filmapp.data.remote.network

import okhttp3.Interceptor
import okhttp3.Response

private const val API_KEY = "8cc6dea6e8f86bf650389d5009fc4980"
class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url
        val request = originalHttpUrl
            .newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build()
        val url = originalRequest
            .newBuilder()
            .url(request)
            .build()
        return chain.proceed(url)
    }
}