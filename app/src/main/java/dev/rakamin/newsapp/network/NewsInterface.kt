package dev.rakamin.newsapp.network

import dev.rakamin.newsapp.models.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsInterface {

    companion object {
        const val API_KEY = "b2a84c73c1e94e6ab564c6f9dddf8e58"
    }

    @GET("v2/top-headlines")
    fun getTopHeadlines(
        @Query("country") country: String = "us",
        @Query("pageSize") pageSize: Int = 5,
        @Query("page") page: Int = 1,
        @Query("apiKey") apiKey: String = API_KEY
    ): Call<NewsResponse>

    @GET("v2/everything")
    fun getEverything(
        @Query("q") q: String = "technology",
        @Query("pageSize") pageSize: Int = 20,
        @Query("page") page: Int = 1,
        @Query("apiKey") apiKey: String = API_KEY
    ): Call<NewsResponse>
}
