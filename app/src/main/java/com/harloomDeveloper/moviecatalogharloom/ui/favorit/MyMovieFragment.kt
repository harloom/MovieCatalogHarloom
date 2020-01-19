package com.harloomDeveloper.moviecatalogharloom.ui.favorit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.harloomDeveloper.moviecatalogharloom.R
import com.harloomDeveloper.moviecatalogharloom.adapter.RcvFavoritMovieAdapter
import com.harloomDeveloper.moviecatalogharloom.data.local.entity.EMovie
import kotlinx.android.synthetic.main.fragment_favorit.*

/**
 * A placeholder fragment containing a simple view.
 */
class MyMovieFragment : Fragment(), RcvFavoritMovieAdapter.Interaction {
    override fun onDeleteTap(position: Int, item: EMovie) {
        context?.let {_context->
            MaterialDialog(_context).show {
                title(R.string.title_alert)
                message(R.string.your_message)
                cornerRadius(8F)
                positiveButton(R.string.agree) { dialog ->
                    pageViewModel.deleteFromFavoritMovie(item)
                    dialog.dismiss()
                }
                negativeButton(R.string.disagree) { dialog ->
                    // Do something
                    dialog.dismiss()
                }
            }
        }


    }

    override fun onItemSelected(position: Int, item: EMovie) {

    }

    private lateinit var pageViewModel: PageViewModel
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var movieAdapter: RcvFavoritMovieAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_favorit, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoading(true)
        initVm()
        initRcv()
    }

    private fun initVm() {
        pageViewModel = ViewModelProviders.of(activity!!).get(PageViewModel::class.java)

    }
    private fun initRcv() {
        movieAdapter = RcvFavoritMovieAdapter(this@MyMovieFragment)
        mRecyclerView =  view!!.findViewById(R.id.rcv_f_movies)
        mRecyclerView.apply {
            adapter = movieAdapter
        }
        pageViewModel.getMovies()?.observe(this@MyMovieFragment, Observer {movie->
            movie?.let {
                showLoading(false)
                movieAdapter.submitList(movie)
                showIndicatorDataNull(movie.isEmpty())

            }


        })



    }

    private  fun showIndicatorDataNull(bool : Boolean){
        if(bool){
            bacground_indicator.visibility = View.VISIBLE
        }else{
            bacground_indicator.visibility = View.GONE
        }
    }
    private fun showLoading(st : Boolean){
        if(st){
            loading_indicator.visibility = View.VISIBLE
        }else{
            loading_indicator.visibility = View.GONE
        }
    }



}