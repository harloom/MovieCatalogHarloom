package com.harloomDeveloper.moviecatalogharloom.ui.movie

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.harloomDeveloper.moviecatalogharloom.MainViewModel
import com.harloomDeveloper.moviecatalogharloom.R
import com.harloomDeveloper.moviecatalogharloom.adapter.RcvMovieAdapter
import com.harloomDeveloper.moviecatalogharloom.data.models.movie.ResultMovie
import com.harloomDeveloper.moviecatalogharloom.Utils
import com.harloomDeveloper.moviecatalogharloom.base.MainModelFactory
import com.harloomDeveloper.moviecatalogharloom.data.local.entity.EMovie

import kotlinx.android.synthetic.main.fragment_movie.*

class MovieFragment : Fragment() {


    private lateinit var mRecyclerView: RecyclerView
    private lateinit var movieAdapter: RcvMovieAdapter
    private var vm : MainViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initRcv()
    }

    private fun initRcv() {
        mRecyclerView.apply {
            adapter = movieAdapter
        }
        vm?.getDataMovie()?.observe(this@MovieFragment, Observer {
            it?.let {
                showLoading(false)
                movieAdapter.submitList(it.resultMovies)
            }


        })



    }


    private fun init(){
        //initializevieModel
        mRecyclerView = view!!.findViewById(R.id.rcv_movies)
        vm = ViewModelProviders.of(activity!! , MainModelFactory(application = activity!!.application)).get(MainViewModel::class.java)
        vm?.setPageMovie(1)
        showLoading(true)
        movieAdapter =
            RcvMovieAdapter(callbackAdaptet)


    }


    private  val   callbackAdaptet : RcvMovieAdapter.Interaction = object :
        RcvMovieAdapter.Interaction {
        override fun onItemLike(item: ResultMovie) {
            val movie  = EMovie(id = item.id,title = item.title,adult = item.adult,backdropPath = item.backdropPath,
                originalLanguage = item.originalLanguage,originalTitle = item.originalTitle,overview = item.overview,
                popularity = item.popularity,posterPath = item.posterPath,releaseDate = item.releaseDate,
                video = item.video,
                voteAverage = item.voteAverage,
                voteCount = item.voteCount)
            vm?.addToFavoritMovie(movie)
        }

        override fun onItemUnlike(item: ResultMovie) {
            item.id?.let {
                vm?.deleteByIdMovie(item.id)
            }

        }

        override fun onItemSelected(position: Int, item: ResultMovie) {
            startActivity(getIntentToDetail(context,item))
        }
    }

    private  fun getIntentToDetail(context: Context?,item: ResultMovie): Intent {
        val intent = Intent(context, DetailMovieActivity::class.java)
        intent.putExtra(Utils.KEY_MOVIE,item)
        return  intent

    }



    private fun showLoading(st : Boolean){
        if(st){
            loading_indicator.visibility = View.VISIBLE
        }else{
            loading_indicator.visibility = View.GONE
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(context, "landscape", Toast.LENGTH_SHORT).show()
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(context, "portrait", Toast.LENGTH_SHORT).show()
        }
    }
}