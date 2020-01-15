package com.harloomDeveloper.moviecatalogharloom

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.harloomDeveloper.moviecatalogharloom.data.api.NetworkBuilder
import com.harloomDeveloper.moviecatalogharloom.data.local.entity.EMovie
import com.harloomDeveloper.moviecatalogharloom.data.local.entity.ETv
import com.harloomDeveloper.moviecatalogharloom.data.local.repository.MovieRepositoryImp
import com.harloomDeveloper.moviecatalogharloom.data.local.repository.TvRepositoryImp
import com.harloomDeveloper.moviecatalogharloom.data.models.movie.Movie
import com.harloomDeveloper.moviecatalogharloom.data.models.movie.ResultMovie
import com.harloomDeveloper.moviecatalogharloom.data.models.tv.ResultTv
import com.harloomDeveloper.moviecatalogharloom.data.models.tv.TvShow
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

class MainViewModel(application: Application) : AndroidViewModel(application) {
    var api = NetworkBuilder.apiService
    var jobLoaded : CompletableJob? =null
    private var mMovieRepositoryImp: MovieRepositoryImp = MovieRepositoryImp(application)
    private var mTvRepositoryImp: TvRepositoryImp = TvRepositoryImp(application)
    private val listMovie : MutableLiveData<Movie> = MutableLiveData()
    private val listTv : MutableLiveData<TvShow> = MutableLiveData()
    fun getDataMovie() : LiveData<Movie> =  listMovie
    fun getDataTv() : LiveData<TvShow> = listTv


    fun addToFavoritTv(data : ETv){
        mTvRepositoryImp.setTv(data)
    }

    fun addToFavoritMovie(data : EMovie){
        mMovieRepositoryImp.setMovie(data)
    }

    fun deleteToFavoritTv(data : ETv){
        mTvRepositoryImp.delete(data)
    }

    fun deleteToFavoritMovie(data : EMovie){
        mMovieRepositoryImp.delete(data)
    }


     fun setPageMovie(page : Int) {
        jobLoaded = Job()
                jobLoaded?.let {
                    CoroutineScope(IO + it).launch {
                        val response =   api.getMovie(page)
                        if(response.isSuccessful){
                            withContext(Main){
                                 listMovie.value = response.body()
                                jobLoaded?.complete()
                            }
                        }else{
                            jobLoaded?.cancel()
                            //error handling
                        }

                    }
                }
            }




    fun setPageTv(page : Int){
        jobLoaded = Job()
                jobLoaded?.let {
                    CoroutineScope(IO + it).launch {
                        val response =   api.getTv(page)
                        if(response.isSuccessful){
                            withContext(Main){
                                listTv.value = response.body()
                                jobLoaded?.complete()
                            }
                        }else{
                            jobLoaded?.cancel()
                            //error handling
                        }

                    }
                }


    }

    fun cancel(){
        jobLoaded?.cancel()
    }

}