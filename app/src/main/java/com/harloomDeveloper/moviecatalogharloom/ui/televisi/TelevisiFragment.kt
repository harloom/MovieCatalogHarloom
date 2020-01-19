package com.harloomDeveloper.moviecatalogharloom.ui.televisi

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat.getSystemService

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.harloomDeveloper.moviecatalogharloom.MainViewModel
import com.harloomDeveloper.moviecatalogharloom.MenuActivity
import com.harloomDeveloper.moviecatalogharloom.R
import com.harloomDeveloper.moviecatalogharloom.adapter.RcvTvAdapter
import com.harloomDeveloper.moviecatalogharloom.data.models.tv.ResultTv
import com.harloomDeveloper.moviecatalogharloom.Utils
import com.harloomDeveloper.moviecatalogharloom.data.local.entity.ETv
import com.harloomDeveloper.moviecatalogharloom.ui.preference.SettingsActivity
import com.like.LikeButton
import com.like.OnLikeListener
import kotlinx.android.synthetic.main.fragment_tv.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TelevisiFragment : Fragment() {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mTvadapter: RcvTvAdapter
    private var vm : MainViewModel? =null
    private var jobSearch : Job? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.toolbar_menu, menu)
        val searchView = SearchView((context as MenuActivity).supportActionBar?.themedContext ?: context)

        menu.findItem(R.id.action_search).apply {
            actionView = searchView
        }.setOnActionExpandListener(object :MenuItem.OnActionExpandListener{
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                menu.setGroupVisible(R.id.menu_container,false)
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                menu.setGroupVisible(R.id.menu_container,true)
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
                        vm?.setSearcTv(newText)
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
                startActivity(Intent(context,SettingsActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }


    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tv, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initRcv()
    }

    private fun initRcv() {
        mRecyclerView.apply {
            adapter = mTvadapter
        }

        vm?.getDataTv()?.observe(this@TelevisiFragment, Observer {
            it?.let {
                showLoading(false)
                mTvadapter.submitList(it.resultTv)
            }
        })

    }


    private fun init(){
        mRecyclerView = view!!.findViewById(R.id.rcv_tv)
        vm =  ViewModelProviders.of(activity!!)
            .get(MainViewModel::class.java)
        vm?.setPageTv(1)
        showLoading(true)
        mTvadapter = RcvTvAdapter(callbackAdaptet)
    }


    private  val   callbackAdaptet : RcvTvAdapter.Interaction = object :
        RcvTvAdapter.Interaction {
        override fun onItemLike(item: ResultTv) {
            val data = ETv(
                id = item.id,
                voteCount = item.voteCount,
                voteAverage = item.voteAverage,
                posterPath = item.posterPath,
                popularity = item.popularity,
                overview = item.overview,
                originalName = item.originalName,
                originalLanguage = item.originalLanguage,
                backdropPath = item.backdropPath,
                title = item.name,
                firstAirDate = item.firstAirDate


            )
            vm?.addToFavoritTv(data);
        }

        override fun onItemUnlike(item: ResultTv) {
          item.id?.let {
              vm?.deleteByIdMTv(item.id)
          }
        }

        override fun onItemSelected(position: Int, item: ResultTv) {
            startActivity(getIntentToDetail(context,item))
        }
    }

    private  fun getIntentToDetail(context: Context?, item: ResultTv): Intent {
        val intent = Intent(context, DetailTvShowActivity::class.java)
        intent.putExtra(Utils.KEY_TvShow,item)
        return  intent

    }

    private fun showLoading(st : Boolean){
        if(st){
            loading_indicator.visibility = View.VISIBLE
        }else{
            loading_indicator.visibility = View.GONE
        }
    }
}