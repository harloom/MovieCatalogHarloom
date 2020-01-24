package com.harloomDeveloper.moviecatalogharloom.ui.favorit

import android.app.Application
import androidx.lifecycle.*
import com.harloomDeveloper.moviecatalogharloom.data.local.entity.EMovie
import com.harloomDeveloper.moviecatalogharloom.data.local.entity.ETv
import com.harloomDeveloper.moviecatalogharloom.data.local.repository.MovieRepositoryImp
import com.harloomDeveloper.moviecatalogharloom.data.local.repository.TvRepositoryImp
import com.harloomDeveloper.moviecatalogharloom.data.models.movie.ResultMovie
import com.harloomDeveloper.moviecatalogharloom.data.models.tv.ResultTv
import com.harloomDeveloper.moviecatalogharloom.data.models.tv.TvShow
import com.harloomDeveloper.moviecatalogharloom.widgets.BannerWidget
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class PageViewModel(application: Application) : AndroidViewModel(application) {
    private val scope = CoroutineScope(IO);
    private var mMovieRepositoryImp:MovieRepositoryImp = MovieRepositoryImp(application)
    private var mTvRepositoryImp:TvRepositoryImp = TvRepositoryImp(application)
    private val mContext = application.baseContext
//    private val listMovie : MutableLiveData<EMovie> = MutableLiveData()
//    private val listTv : MutableLiveData<ETv> = MutableLiveData()

    fun deleteFromFavoritTv(data : ETv){
        scope.launch {
            mTvRepositoryImp.delete(data)
        }

    }

    fun deleteFromFavoritMovie(data : EMovie){
        scope.launch {
            mMovieRepositoryImp.delete(data)
            BannerWidget.refress(mContext)
        }

    }

    fun getMovies()  = mMovieRepositoryImp.getMovies()
    fun getTvs() = mTvRepositoryImp.getTv()



}