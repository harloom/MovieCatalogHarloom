package com.harloomDeveloper.moviecatalogharloom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.harloomDeveloper.moviecatalogharloom.data.DataSource
import com.harloomDeveloper.moviecatalogharloom.data.Movies

class MainActivity : AppCompatActivity(), RcvMovieAdapter.Interaction {


    override fun onItemSelected(position: Int, item: Movies) {
        startActivity(getIntentMovie(item))

    }

    private fun getIntentMovie( item: Movies): Intent{
        val intent = Intent(this@MainActivity,DetailMovieActivity::class.java)
        intent.putExtra(utils.KEY_MOVIE,item)
        return  intent
    }

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mListMovies: List<Movies>
    private lateinit var movieAdapter: RcvMovieAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
        mRecyclerView = findViewById(R.id.rcv_movies)
        mListMovies = DataSource().getListMovide()
        movieAdapter = RcvMovieAdapter(this@MainActivity)
    }


}
