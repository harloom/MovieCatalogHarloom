package com.harloomDeveloper.moviecatalogharloom.ui.televisi

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.harloomDeveloper.moviecatalogharloom.MainViewModel
import com.harloomDeveloper.moviecatalogharloom.R
import com.harloomDeveloper.moviecatalogharloom.adapter.RcvTvAdapter
import com.harloomDeveloper.moviecatalogharloom.data.models.tv.ResultTv
import com.harloomDeveloper.moviecatalogharloom.Utils
import com.harloomDeveloper.moviecatalogharloom.base.MainModelFactory
import com.harloomDeveloper.moviecatalogharloom.data.local.entity.ETv
import com.like.LikeButton
import com.like.OnLikeListener
import kotlinx.android.synthetic.main.fragment_tv.*

class TelevisiFragment : Fragment() {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mTvadapter: RcvTvAdapter
    private var vm : MainViewModel? =null
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
        vm =  ViewModelProviders.of(activity!! , MainModelFactory(application = activity!!.application))
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