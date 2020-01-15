package com.harloomDeveloper.moviecatalogharloom.ui.favorit

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.harloomDeveloper.moviecatalogharloom.data.local.repository.MovieRepositoryImp
import com.harloomDeveloper.moviecatalogharloom.data.local.repository.TvRepositoryImp
import com.harloomDeveloper.moviecatalogharloom.data.models.movie.ResultMovie
import com.harloomDeveloper.moviecatalogharloom.data.models.tv.ResultTv

class PageViewModel(application: Application) : ViewModel() {

    private var mMovieRepositoryImp:MovieRepositoryImp = MovieRepositoryImp(application)
    private var mTvRepositoryImp:TvRepositoryImp = TvRepositoryImp(application)

    private val _index = MutableLiveData<Int>()
    val text: LiveData<String> = Transformations.map(_index) {
        "Hello world from section: $it"
    }

    fun setIndex(index: Int) {
        _index.value = index
    }

    fun deleteToFavoritTv(data : ResultTv){
        mTvRepositoryImp.delete(data)
    }

    fun deleteToFavoritMovie(data : ResultMovie){
        mMovieRepositoryImp.delete(data)
    }



    /**
     * fungsi qeury live data
     *
     *
     * */
}