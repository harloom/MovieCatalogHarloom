package com.harloomDeveloper.moviecatalogharloom

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.harloomDeveloper.moviecatalogharloom.data.api.NetworkBuilder
import com.harloomDeveloper.moviecatalogharloom.data.models.movie.Movie
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

class MainViewModel : ViewModel() {
    var api = NetworkBuilder.apiService
    var jobLoaded : CompletableJob? =null


     fun getMovieList(page : Int) : LiveData<Movie?> {
        jobLoaded = Job()
        return  object : LiveData<Movie?>() {
            override fun onActive() {
                super.onActive()
                jobLoaded?.let {
                    CoroutineScope(IO + it).launch {
                        val response =   api.getMovie(page)
                        if(response.isSuccessful){
                            withContext(Main){
                                value = response.body()
                                jobLoaded?.complete()
                            }
                        }else{
                            jobLoaded?.cancel()
                            //error handling
                        }

                    }
                }
            }

        }
    }


    fun getTvList(){

    }

    fun cancel(){
        jobLoaded?.cancel()
    }

}