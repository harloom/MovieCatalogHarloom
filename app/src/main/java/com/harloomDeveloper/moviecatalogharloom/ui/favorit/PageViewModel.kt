package com.harloomDeveloper.moviecatalogharloom.ui.favorit

import android.app.Application
import androidx.lifecycle.*
import com.harloomDeveloper.moviecatalogharloom.data.local.entity.EMovie
import com.harloomDeveloper.moviecatalogharloom.data.local.entity.ETv
import com.harloomDeveloper.moviecatalogharloom.data.local.repository.MovieRepositoryImp
import com.harloomDeveloper.moviecatalogharloom.data.local.repository.TvRepositoryImp
import com.harloomDeveloper.moviecatalogharloom.data.models.movie.ResultMovie
import com.harloomDeveloper.moviecatalogharloom.data.models.tv.ResultTv

class PageViewModel(application: Application) : AndroidViewModel(application) {

    private var mMovieRepositoryImp:MovieRepositoryImp = MovieRepositoryImp(application)
    private var mTvRepositoryImp:TvRepositoryImp = TvRepositoryImp(application)

    private val _index = MutableLiveData<Int>()
    val text: LiveData<String> = Transformations.map(_index) {
        "Hello world from section: $it"
    }

    fun setIndex(index: Int) {
        _index.value = index
    }

    fun deleteToFavoritTv(data : ETv){
        mTvRepositoryImp.delete(data)
    }

    fun deleteToFavoritMovie(data : EMovie){
        mMovieRepositoryImp.delete(data)
    }



    /**
     * fungsi qeury live data
     *
     *
     * */
}