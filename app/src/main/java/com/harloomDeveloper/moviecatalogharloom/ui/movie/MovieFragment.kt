package com.harloomDeveloper.moviecatalogharloom.ui.movie

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.provider.Settings
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.harloomDeveloper.moviecatalogharloom.MainViewModel
import com.harloomDeveloper.moviecatalogharloom.MenuActivity
import com.harloomDeveloper.moviecatalogharloom.R
import com.harloomDeveloper.moviecatalogharloom.adapter.RcvMovieAdapter
import com.harloomDeveloper.moviecatalogharloom.data.models.movie.ResultMovie
import com.harloomDeveloper.moviecatalogharloom.Utils
import com.harloomDeveloper.moviecatalogharloom.Utils.StateQuerySearch
import com.harloomDeveloper.moviecatalogharloom.data.local.entity.EMovie
import com.harloomDeveloper.moviecatalogharloom.ui.preference.SettingsActivity

import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MovieFragment : Fragment(R.layout.fragment_movie) {


    private lateinit var mRecyclerView: RecyclerView
    private lateinit var movieAdapter: RcvMovieAdapter
    private var vm : MainViewModel? = null

    private var jobSearch : Job? =null
    private var querySearch : String?= null
    private var isExpand : Boolean  =false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {activity->
            vm = ViewModelProvider(activity).get(MainViewModel::class.java)

        }

        if (savedInstanceState != null) {
            val result = savedInstanceState.getString(StateQuerySearch) as String?
            querySearch = result
            isExpand  = savedInstanceState.getBoolean("isExpand")
        }


        if(cekInitial()){
            vm?.setPageMovie(1)
        }
        setHasOptionsMenu(true)
    }

    private  fun cekInitial(): Boolean {
        return !isExpand && querySearch ==null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()

        inflater.inflate(R.menu.toolbar_menu, menu)
        val searchView = SearchView((context as MenuActivity).supportActionBar?.themedContext ?: context)
        val search = menu.findItem(R.id.action_search).apply {
            actionView = searchView

        }

        if(isExpand && querySearch !=null){
            search.expandActionView()
            searchView.setQuery(querySearch,true)

        }

        search.setOnActionExpandListener(object :MenuItem.OnActionExpandListener{
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                isExpand = true
                menu.setGroupVisible(R.id.menu_container,false)
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                menu.setGroupVisible(R.id.menu_container,true)
                isExpand = false
                activity?.invalidateOptionsMenu()
                return true
            }

        })





        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if(newText.isNotBlank()){
                    //debounce Reactive
                    jobSearch?.cancel()
                    jobSearch = CoroutineScope(Main).launch {
                        delay(300)
                        showLoading(true)
                        querySearch = newText
                        vm?.setSearcMovie(newText)
                    }
                }


                return true
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_change_language->{
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                true


            }
            R.id.action_settings_reminder->{
                startActivity(Intent(context, SettingsActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        querySearch?.let {
            outState.putString(StateQuerySearch, querySearch)
            outState.putBoolean("isExpand",isExpand)
        }

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

    override fun onDestroyView() {
        super.onDestroyView()
        isExpand = false
        querySearch =null

    }
}