package com.harloomDeveloper.moviecatalogharloom.ui.movie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.harloomDeveloper.moviecatalogharloom.DetailMovieActivity
import com.harloomDeveloper.moviecatalogharloom.R
import com.harloomDeveloper.moviecatalogharloom.RcvMovieAdapter
import com.harloomDeveloper.moviecatalogharloom.data.DataSourceMovie
import com.harloomDeveloper.moviecatalogharloom.data.Movies
import com.harloomDeveloper.moviecatalogharloom.utils

class MovieFragment : Fragment() {
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mListMovies: List<Movies>
    private lateinit var movieAdapter: RcvMovieAdapter


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
        movieAdapter.submitList(mListMovies)
        mRecyclerView.apply {
            adapter = movieAdapter
        }
    }


    private fun init(){
        mRecyclerView = view!!.findViewById(R.id.rcv_movies)
        mListMovies = DataSourceMovie().getListMovide()
        movieAdapter = RcvMovieAdapter(callbackAdaptet)
    }

    private  val   callbackAdaptet : RcvMovieAdapter.Interaction = object :
        RcvMovieAdapter.Interaction {
        override fun onItemSelected(position: Int, item: Movies) {
            startActivity(getIntentToDetail(context,item))
        }
    }

    private  fun getIntentToDetail(context: Context?,item: Movies): Intent {
        val intent = Intent(context, DetailMovieActivity::class.java)
        intent.putExtra(utils.KEY_MOVIE,item)
        return  intent

    }
}