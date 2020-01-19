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
    private val scope : CoroutineScope = CoroutineScope(IO)
    var api = NetworkBuilder.apiService
    var jobLoaded : CompletableJob? =null
    private var mMovieRepositoryImp: MovieRepositoryImp = MovieRepositoryImp(application)
    private var mTvRepositoryImp: TvRepositoryImp = TvRepositoryImp(application)
    private val listMovie : MutableLiveData<Movie> = MutableLiveData()
    private val listTv : MutableLiveData<TvShow> = MutableLiveData()
    fun getDataMovie() : LiveData<Movie> =  listMovie
    fun getDataTv() : LiveData<TvShow> = listTv


    fun addToFavoritTv(data : ETv){
        scope.launch {
            mTvRepositoryImp.setTv(data)
        }

    }

    fun addToFavoritMovie(data : EMovie){
        scope.launch {
            mMovieRepositoryImp.setMovie(data)
        }

    }

    fun deleteFromFavoritTv(data : ETv){
        scope.launch {
            mTvRepositoryImp.delete(data)
        }

    }

    fun deletFromFavoritMovie(data : EMovie){
        scope.launch {
            mMovieRepositoryImp.delete(data)
        }
        }


    fun deleteByIdMovie(id : Int){
        scope.launch {
            mMovieRepositoryImp.deleteById(id)
        }

    }
    fun deleteByIdMTv(id : Int){
        scope.launch {
            mTvRepositoryImp.deleteById(id)
        }

    }

    fun setSearcMovie(text : String){
        jobLoaded = Job()
        jobLoaded?.let {
            CoroutineScope(IO+it).launch {
                val response  = api.getSearchMovie(text)
                if(response.isSuccessful){
                  val movie  = response.body()
                    val jobMap = launch {
                        movie?.let { m->
                            m.resultMovies.map {resultMovie ->
                                val query = resultMovie.id?.let { it1 ->
                                    mMovieRepositoryImp.isFavoirt(
                                        it1
                                    )
                                }
                                resultMovie.isFavorit = query !=null && query>0
                            }
                        }
                    }
                    jobMap.invokeOnCompletion {
                        CoroutineScope(Main).launch {
                            listMovie.value = movie
                            jobLoaded?.complete()
                        }
                    }
                }else{

                    jobLoaded?.cancel()
                }
            }
        }
    }

    fun setSearcTv(text : String){
        jobLoaded = Job()
        jobLoaded?.let {
            CoroutineScope(IO + it).launch {
                val response =   api.getSearchTv(text)
                if(response.isSuccessful){

                    val tv = response.body()
                    val jobMap = launch {
                        tv?.resultTv?.map {tv->
                            val query = tv.id?.let {
                                    it1 -> mTvRepositoryImp.isFavoirt(it1) }
                            tv.isFavorit = query !=null && query>0
                        }
                    }
                    jobMap.invokeOnCompletion {
                        CoroutineScope(Main).launch {
                            listTv.value = tv
                            jobLoaded?.complete()
                        }

                    }


                }else{
                    jobLoaded?.cancel()
                    //error handling
                }

            }
        }


    }


     fun setPageMovie(page : Int) {

        jobLoaded = Job()
                jobLoaded?.let {
                    CoroutineScope(IO + it).launch {
                        val response =   api.getMovie(page)
                        if(response.isSuccessful){
                                 val movie = response.body()
                                 val jobMap = launch {
                                    movie?.let { m->
                                        m.resultMovies.map {resultMovie ->
                                            val query = resultMovie.id?.let { it1 ->
                                                mMovieRepositoryImp.isFavoirt(
                                                    it1
                                                )
                                            }
                                            resultMovie.isFavorit = query !=null && query>0
                                        }
                                    }
                                }
                                jobMap.invokeOnCompletion {
                                    CoroutineScope(Main).launch {
                                        listMovie.value = movie
                                        jobLoaded?.complete()
                                    }
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

                               val tv = response.body()
                                val jobMap = launch {
                                    tv?.resultTv?.map {tv->
                                        val query = tv.id?.let { it1 ->
                                            mTvRepositoryImp.isFavoirt(
                                                it1
                                            )
                                        }
                                        tv.isFavorit = query !=null && query>0
                                    }
                                }
                            jobMap.invokeOnCompletion {
                                CoroutineScope(Main).launch {
                                    listTv.value = tv
                                    jobLoaded?.complete()
                                }

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

//    override fun onCleared() {
//        print("ViewModel Clear")
//        super.onCleared()
//    }

}