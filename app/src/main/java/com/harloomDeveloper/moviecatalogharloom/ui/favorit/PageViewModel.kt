package com.harloomDeveloper.moviecatalogharloom.ui.favorit

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.harloomDeveloper.moviecatalogharloom.data.local.repository.MovieRepository
import com.harloomDeveloper.moviecatalogharloom.data.local.repository.TvRepository

class PageViewModel(application: Application) : ViewModel() {

    private var mMovieRepository:MovieRepository = MovieRepository(application)
    private var mTvRepository:TvRepository = TvRepository(application)

    private val _index = MutableLiveData<Int>()
    val text: LiveData<String> = Transformations.map(_index) {
        "Hello world from section: $it"
    }

    fun setIndex(index: Int) {
        _index.value = index
    }


    /**
     * fungsi qeury live data
     *
     *
     * */
}