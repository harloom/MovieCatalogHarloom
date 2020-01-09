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
import com.harloomDeveloper.moviecatalogharloom.DetailTvShowActivity
import com.harloomDeveloper.moviecatalogharloom.MainViewModel
import com.harloomDeveloper.moviecatalogharloom.R
import com.harloomDeveloper.moviecatalogharloom.adapter.RcvTvAdapter
import com.harloomDeveloper.moviecatalogharloom.data.DataSourceTV
import com.harloomDeveloper.moviecatalogharloom.data.models.tv.ResultTv
import com.harloomDeveloper.moviecatalogharloom.utils
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

        vm?.getTvList(1)?.observe(this@TelevisiFragment, Observer {
            it?.let {
                showLoading(false)
                mTvadapter.submitList(it.resultTv)
            }
        })

    }


    private fun init(){
        mRecyclerView = view!!.findViewById(R.id.rcv_tv)
        vm = ViewModelProviders.of(activity!!)[MainViewModel::class.java]
        showLoading(true)
        mTvadapter = RcvTvAdapter(callbackAdaptet)
    }

    private  val   callbackAdaptet : RcvTvAdapter.Interaction = object :
        RcvTvAdapter.Interaction {
        override fun onItemSelected(position: Int, item: ResultTv) {
            startActivity(getIntentToDetail(context,item))
        }
    }

    private  fun getIntentToDetail(context: Context?, item: ResultTv): Intent {
        val intent = Intent(context, DetailTvShowActivity::class.java)
        intent.putExtra(utils.KEY_TvShow,item)
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