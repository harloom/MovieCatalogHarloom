package com.harloomDeveloper.moviecatalogharloom.data.api

import android.util.Log
import com.harloomDeveloper.moviecatalogharloom.data.models.movie.Movie
import com.harloomDeveloper.moviecatalogharloom.data.models.tv.TvShow
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*



object NetworkBuilder {
//    https://api.themoviedb.org/3/movie/76341?api_key=<<api_key>>&language=de
        private val retfoit: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .client(OkHttpClient.Builder()
                .addInterceptor(RequestService() )
                .addInterceptor (LogNetwork())
                .build())
            .addConverterFactory(GsonConverterFactory.create())
    }

    private fun LogNetwork(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return interceptor
    }
    val apiService: MovieApi by lazy {
        retfoit.build().create(MovieApi::class.java)
    }
}

class RequestService : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        var request  = chain.request()
        val url = request.url.newBuilder()
            .addQueryParameter("api_key", Constant.KEY_API)
            .addQueryParameter("language", Locale.getDefault().language)
            .build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }

}

interface MovieApi{
    @GET("movie/now_playing")
    suspend fun getMovie(@Query("page") page : Int) : retrofit2.Response<Movie>

    @GET("movie/{id}")
    suspend fun getDetailMovie(
        @Path("id") id : String
    )

    @GET("tv/airing_today")
    suspend fun getTv(
        @Query("page") page : Int
    ): retrofit2.Response<TvShow>

}